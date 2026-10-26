package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.effect.ModEffects;
import com.nekyoh.epiccraft.common.util.AttackStrength;
import com.nekyoh.epiccraft.common.util.CombatUtil;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/**
 * Monarch Sword — a espada mais poderosa do mod.
 * - Modelo grande e alcance maior (+1 bloco).
 * - Ataque lento (cooldown maior), mas todo golpe carregado solta uma onda roxa
 *   enorme (raio 5) que acerta tudo em volta com 60% do dano e empurra para longe.
 * - Alvo principal: Sangramento II + Fraqueza + Lentidão.
 * - Alvos da onda: Sangramento I.
 */
public class MonarchSwordItem extends EpicSwordItem {
    private static final double RADIUS = 5.0D;
    private static final DustParticleOptions PURPLE = new DustParticleOptions(new Vector3f(0.55F, 0.1F, 0.9F), 1.6F);
    private static final DustParticleOptions DARK_PURPLE = new DustParticleOptions(new Vector3f(0.3F, 0.0F, 0.5F), 2.0F);

    public MonarchSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level) {
            target.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 120, 1, false, false, true), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1), attacker);

            // Efeito roxo no golpe
            level.sendParticles(PURPLE, target.getX(), target.getY(0.5D), target.getZ(), 25, 0.4D, 0.6D, 0.4D, 0.0D);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, target.getX(), target.getY(0.5D), target.getZ(), 20, 0.3D, 0.5D, 0.3D, 0.05D);

            if (AttackStrength.isFull(attacker)) {
                shockwave(level, target, attacker);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private static void shockwave(ServerLevel level, LivingEntity target, LivingEntity attacker) {
        float damage = CombatUtil.currentAttackDamage(attacker) * 0.6F;
        Vec3 center = target.position();

        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class,
                target.getBoundingBox().inflate(RADIUS, 2.0D, RADIUS))) {
            if (victim == target || !CombatUtil.canAreaHit(attacker, victim)) continue;
            if (victim.position().distanceTo(center) > RADIUS) continue;

            if (victim.hurt(CombatUtil.meleeSource(attacker), damage)) {
                victim.knockback(1.0D, center.x - victim.getX(), center.z - victim.getZ());
                victim.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 80, 0, false, false, true), attacker);
            }
        }

        // Anel roxo se espalhando no chão
        for (int ring = 1; ring <= 5; ring++) {
            double r = ring;
            int points = ring * 12;
            for (int i = 0; i < points; i++) {
                double angle = (Math.PI * 2 * i) / points;
                double x = center.x + Math.cos(angle) * r;
                double z = center.z + Math.sin(angle) * r;
                level.sendParticles(ring % 2 == 0 ? DARK_PURPLE : PURPLE, x, center.y + 0.15D, z, 1, 0.0D, 0.05D, 0.0D, 0.0D);
            }
        }
        level.sendParticles(ParticleTypes.DRAGON_BREATH, center.x, center.y + 0.5D, center.z, 40, RADIUS * 0.4D, 0.3D, RADIUS * 0.4D, 0.02D);

        level.playSound(null, center.x, center.y, center.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.2F, 0.6F);
        level.playSound(null, center.x, center.y, center.z, SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.7F);
    }
}
