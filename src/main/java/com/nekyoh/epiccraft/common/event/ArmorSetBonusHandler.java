package com.nekyoh.epiccraft.common.event;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.enchantment.ModEnchantments;
import com.nekyoh.epiccraft.common.item.properties.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Bônus de conjunto das armaduras — REESCRITO.
 *
 * Antes os bônus eram poções reaplicadas todo tick e removidas quando a armadura
 * saía. Isso causava os bugs: tirar a armadura apagava a poção de Força/Velocidade
 * que o jogador tinha bebido, maçã dourada perdia o Health Boost, etc.
 *
 * Agora:
 *  - Força, Velocidade, Vida extra, Altura de passo e Queda usam MODIFICADORES DE
 *    ATRIBUTO com ID próprio. Eles somam com poções/beacon e são removidos sem
 *    tocar em nada de fora.
 *  - Resistência ao fogo e Herói da Vila continuam como efeito (precisam ser efeito),
 *    mas com duração curta renovada e NUNCA removidos à força: quando a armadura sai,
 *    eles acabam sozinhos em ~3s. Uma poção mais forte/longa nunca é sobrescrita.
 */
@EventBusSubscriber(modid = EpicCraft.MODID)
public final class ArmorSetBonusHandler {
    private static final ResourceLocation NEZONITA_STRENGTH = EpicCraft.id("armor.nezonita_strength");
    private static final ResourceLocation NEZONITA_HEALTH = EpicCraft.id("armor.nezonita_health");
    private static final ResourceLocation TEKYNITA_SPEED = EpicCraft.id("armor.tekynita_speed");
    private static final ResourceLocation TEKYNITA_STEP = EpicCraft.id("armor.tekynita_step");
    private static final ResourceLocation TEKYNITA_FALL = EpicCraft.id("armor.tekynita_fall");

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || player.tickCount % 5 != 0) return;

        // ---------------- NEZONITA ----------------
        boolean nezonitaFull = isFullSet(player, ModArmorMaterials.NEZONITA);
        boolean nezonitaChest = ModArmorMaterials.is(player.getItemBySlot(EquipmentSlot.CHEST), ModArmorMaterials.NEZONITA);
        // Força II = +6 de dano, Força I = +3 (mesmos valores da poção)
        setModifier(player, Attributes.ATTACK_DAMAGE, NEZONITA_STRENGTH,
                nezonitaFull ? 6.0D : nezonitaChest ? 3.0D : 0.0D, AttributeModifier.Operation.ADD_VALUE);
        // Health Boost III = +12 de vida (6 corações)
        setModifier(player, Attributes.MAX_HEALTH, NEZONITA_HEALTH,
                nezonitaFull ? 12.0D : 0.0D, AttributeModifier.Operation.ADD_VALUE);

        // ---------------- TEKYNITA ----------------
        boolean tekynitaFull = isFullSet(player, ModArmorMaterials.TEKYNITA);
        boolean tekynitaBoots = ModArmorMaterials.is(player.getItemBySlot(EquipmentSlot.FEET), ModArmorMaterials.TEKYNITA);
        // Velocidade III = +60%, Velocidade I = +20%
        setModifier(player, Attributes.MOVEMENT_SPEED, TEKYNITA_SPEED,
                tekynitaFull ? 0.6D : tekynitaBoots ? 0.2D : 0.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        // Sobe 1 bloco sem pular
        setModifier(player, Attributes.STEP_HEIGHT, TEKYNITA_STEP,
                tekynitaFull ? 0.4D : 0.0D, AttributeModifier.Operation.ADD_VALUE);
        // 80% menos dano de queda
        setModifier(player, Attributes.FALL_DAMAGE_MULTIPLIER, TEKYNITA_FALL,
                tekynitaFull ? -0.8D : 0.0D, AttributeModifier.Operation.ADD_VALUE);

        if (nezonitaFull || tekynitaFull) {
            keepEffect(player, MobEffects.FIRE_RESISTANCE, 0);
        }

        // ---------------- EMERALD ----------------
        if (isFullSet(player, ModArmorMaterials.EMERALD)) {
            int level = Math.min(2, getMinEmeraldGraceLevel(player));
            keepEffect(player, MobEffects.HERO_OF_THE_VILLAGE, level);
        }
    }

    /** Adiciona/atualiza/remove um modificador de atributo só nosso. amount = 0 remove. */
    private static void setModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id,
                                    double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null) return;

        AttributeModifier current = instance.getModifier(id);
        if (amount == 0.0D) {
            if (current != null) {
                instance.removeModifier(id);
                if (attribute == Attributes.MAX_HEALTH && entity.getHealth() > entity.getMaxHealth()) {
                    entity.setHealth(entity.getMaxHealth());
                }
            }
            return;
        }
        if (current != null && current.amount() == amount && current.operation() == operation) return;
        if (current != null) instance.removeModifier(id);
        instance.addTransientModifier(new AttributeModifier(id, amount, operation));
    }

    /**
     * Garante o efeito com duração curta. Não mexe se já existe um efeito igual ou mais
     * forte com duração sobrando (ex.: poção bebida).
     */
    private static void keepEffect(Player player, Holder<MobEffect> effect, int amplifier) {
        MobEffectInstance current = player.getEffect(effect);
        if (current != null && current.getAmplifier() >= amplifier && !current.endsWithin(40)) return;
        // ambient = true, sem partículas, com ícone
        player.addEffect(new MobEffectInstance(effect, 80, amplifier, true, false, true));
    }

    private static boolean isFullSet(Player player, DeferredHolder<ArmorMaterial, ArmorMaterial> material) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (!ModArmorMaterials.is(player.getItemBySlot(slot), material)) return false;
        }
        return true;
    }

    private static int getMinEmeraldGraceLevel(Player player) {
        return ModEnchantments.get(player.level().registryAccess(), ModEnchantments.EMERALD_GRACE)
                .map(holder -> {
                    int min = Integer.MAX_VALUE;
                    for (EquipmentSlot slot : ARMOR_SLOTS) {
                        min = Math.min(min, EnchantmentHelper.getItemEnchantmentLevel(holder, player.getItemBySlot(slot)));
                    }
                    return min == Integer.MAX_VALUE ? 0 : min;
                })
                .orElse(0);
    }

    private ArmorSetBonusHandler() {}
}
