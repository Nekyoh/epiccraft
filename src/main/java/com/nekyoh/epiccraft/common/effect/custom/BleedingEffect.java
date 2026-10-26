package com.nekyoh.epiccraft.common.effect.custom;

import com.nekyoh.epiccraft.common.damage.ModDamageTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import org.joml.Vector3f;

public class BleedingEffect extends MobEffect {
    private static final DustParticleOptions BLOOD = new DustParticleOptions(new Vector3f(0.5F, 0.0F, 0.0F), 1.0F);

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0x8A0303);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            entity.hurt(ModDamageTypes.bleeding(serverLevel), 1.0F + amplifier);

            serverLevel.sendParticles(BLOOD,
                    entity.getX(), entity.getY(0.5D), entity.getZ(),
                    5, 0.2D, 0.4D, 0.2D, 0.05D);
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
                    entity.getX(), entity.getY(0.5D), entity.getZ(),
                    4, 0.1D, 0.2D, 0.1D, 0.02D);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Nível 1: a cada 25 ticks, nível 2: a cada 12, nível 3: a cada 6...
        int interval = 25 >> amplifier;
        return interval <= 0 || duration % interval == 0;
    }
}
