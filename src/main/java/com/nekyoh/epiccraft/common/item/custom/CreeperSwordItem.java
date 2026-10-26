package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.util.AttackStrength;
import com.nekyoh.epiccraft.common.util.CombatUtil;
import com.nekyoh.epiccraft.config.Config;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;

/**
 * Creeper Sword: golpes carregados têm chance de explodir a área do alvo.
 * A explosão não quebra blocos, e o jogador leva um dano pequeno (configurável).
 */
public class CreeperSwordItem extends EpicSwordItem {
    private static final double RADIUS = 3.5D;
    private static final float EXPLOSION_DAMAGE = 8.0F;

    public CreeperSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level
                && AttackStrength.isFull(attacker)
                && attacker.getRandom().nextDouble() < Config.CREEPER_SWORD_CHANCE.get()) {
            explode(level, target, attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private static void explode(ServerLevel level, LivingEntity target, LivingEntity attacker) {
        Vec3 center = target.position().add(0, target.getBbHeight() * 0.5D, 0);

        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y, center.z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.POOF, center.x, center.y, center.z, 30, 1.2D, 0.6D, 1.2D, 0.05D);
        level.playSound(null, center.x, center.y, center.z, SoundEvents.GENERIC_EXPLODE.value(),
                SoundSource.PLAYERS, 1.2F, 1.0F + (level.random.nextFloat() - 0.5F) * 0.2F);

        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class,
                target.getBoundingBox().inflate(RADIUS))) {
            if (!CombatUtil.canAreaHit(attacker, victim)) continue;
            double dist = victim.position().distanceTo(target.position());
            if (dist > RADIUS) continue;

            float falloff = (float) (1.0D - dist / (RADIUS + 1.0D));
            victim.hurt(level.damageSources().explosion(attacker, attacker), EXPLOSION_DAMAGE * falloff);

            Vec3 push = victim.position().subtract(target.position()).multiply(1, 0, 1);
            if (push.lengthSqr() < 1.0E-4D) push = new Vec3(0, 0, 0);
            else push = push.normalize();
            double resist = 1.0D - victim.getAttributeValue(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE);
            victim.push(push.x * 0.8D * falloff * resist, 0.35D * falloff * resist, push.z * 0.8D * falloff * resist);
            victim.hurtMarked = true;
        }

        // Recuo: o jogador leva um pouco de dano
        float selfDamage = Config.CREEPER_SWORD_SELF_DAMAGE.get().floatValue();
        if (selfDamage > 0.0F) {
            attacker.hurt(level.damageSources().explosion(null, null), selfDamage);
        }
    }
}
