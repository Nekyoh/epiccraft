package com.nekyoh.epiccraft.common.event;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.item.custom.BlockingSwordItem;
import com.nekyoh.epiccraft.common.item.custom.FryingPanItem;
import com.nekyoh.epiccraft.common.util.AttackStrength;
import com.nekyoh.epiccraft.common.util.CombatUtil;
import com.nekyoh.epiccraft.config.Config;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Sistema de combate do mod:
 *  - Adagas duplas (uma em cada mão)
 *  - Backstab com adaga
 *  - Defesa estilo 1.8 com katana / taco
 *  - Efeitos extras da frigideira ao defender
 */
@EventBusSubscriber(modid = EpicCraft.MODID)
public final class CombatEvents {
    public static final TagKey<Item> DAGGERS = TagKey.create(Registries.ITEM, EpicCraft.id("daggers"));
    private static final ResourceLocation DUAL_WIELD_ID = EpicCraft.id("dagger_dual_wield");

    // =====================================================================
    // Início do ataque: guarda a força da barra e anima a segunda adaga
    // =====================================================================
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        AttackStrength.record(player);

        if (isDualWielding(player) && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.swing(InteractionHand.OFF_HAND, true);
        }
    }

    // =====================================================================
    // Adagas duplas:
    //   dano = dano da adaga mais forte + (dano principal + dano secundária) * bônus
    //   (bônus padrão 0.5 = metade da soma, configurável)
    // É aplicado como modificador de atributo, então funciona com crítico,
    // encantamentos, força e a barra de ataque normalmente.
    // =====================================================================
    @SubscribeEvent
    public static void updateDualWield(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage == null) return;

        double bonus = 0.0D;
        if (isDualWielding(player)) {
            double main = weaponDamage(player.getMainHandItem());
            double off = weaponDamage(player.getOffhandItem());
            double strongest = Math.max(main, off);
            bonus = (strongest - main) + (main + off) * Config.DAGGER_DUAL_WIELD_BONUS.get();
        }

        AttributeModifier current = attackDamage.getModifier(DUAL_WIELD_ID);
        if (bonus <= 0.0D) {
            if (current != null) attackDamage.removeModifier(DUAL_WIELD_ID);
        } else if (current == null || current.amount() != bonus) {
            if (current != null) attackDamage.removeModifier(DUAL_WIELD_ID);
            attackDamage.addTransientModifier(new AttributeModifier(DUAL_WIELD_ID, bonus, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static boolean isDualWielding(Player player) {
        return player.getMainHandItem().is(DAGGERS) && player.getOffhandItem().is(DAGGERS);
    }

    /** Dano total da arma (1 do punho + modificadores de dano do item na mão principal). */
    private static double weaponDamage(ItemStack stack) {
        double[] total = {1.0D};
        stack.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == Attributes.ATTACK_DAMAGE.value()
                    && modifier.operation() == AttributeModifier.Operation.ADD_VALUE) {
                total[0] += modifier.amount();
            }
        });
        return total[0];
    }

    // =====================================================================
    // Dano recebido
    // =====================================================================
    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        if (victim.level().isClientSide) return;

        // ---------- Backstab com adaga ----------
        if (source.is(DamageTypes.PLAYER_ATTACK)
                && source.getEntity() instanceof Player attacker
                && source.getDirectEntity() == attacker
                && attacker.getMainHandItem().is(DAGGERS)
                && CombatUtil.isBehind(victim, attacker)) {

            event.setAmount(event.getAmount() * Config.DAGGER_BACKSTAB_MULTIPLIER.get().floatValue());
            if (victim.level() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.CRIT, victim.getX(), victim.getY(0.6D), victim.getZ(),
                        12, 0.3D, 0.3D, 0.3D, 0.2D);
                level.playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                        SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.3F);
            }
        }

        if (!(victim instanceof Player player)) return;

        // ---------- Defesa com katana / taco (estilo 1.8) ----------
        if (BlockingSwordItem.isBlocking(player) && canBeBlocked(player, source)) {
            float reduction = Config.SWORD_BLOCK_REDUCTION.get().floatValue();
            event.setAmount(event.getAmount() * (1.0F - reduction));

            ItemStack blocking = player.getUseItem();
            blocking.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 0.8F, 1.4F + player.getRandom().nextFloat() * 0.2F);
            return;
        }

        // ---------- Frigideira (escudo de verdade): som de "TONG" e empurrão ----------
        if (player.isUsingItem() && player.getUseItem().getItem() instanceof FryingPanItem
                && player.isDamageSourceBlocked(source)) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.8F, 2.0F);
            Entity attacker = source.getDirectEntity();
            if (attacker instanceof LivingEntity living) {
                Vec3 look = player.getLookAngle();
                living.knockback(0.6D, -look.x, -look.z);
            }
        }
    }

    /** Ataques que dá para defender: tem origem, vêm da frente e não ignoram escudo. */
    private static boolean canBeBlocked(Player player, DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_SHIELD)) return false;
        Vec3 from = source.getSourcePosition();
        return from != null && CombatUtil.isInFront(player, from);
    }

    private CombatEvents() {}
}
