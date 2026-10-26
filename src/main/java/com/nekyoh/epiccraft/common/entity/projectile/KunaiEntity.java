package com.nekyoh.epiccraft.common.entity.projectile;

import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/** Kunai arremessada: crava no bloco e pode ser recolhida igual flecha. */
public class KunaiEntity extends AbstractArrow {
    private static final double BASE_DAMAGE = 2.6D;

    public KunaiEntity(EntityType<? extends KunaiEntity> type, Level level) {
        super(type, level);
        this.setBaseDamage(BASE_DAMAGE);
    }

    public KunaiEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.KUNAI.get(), owner, level, stack, null);
        this.setBaseDamage(BASE_DAMAGE);
    }

    public KunaiEntity(Level level, double x, double y, double z, ItemStack stack, @Nullable ItemStack weapon) {
        super(ModEntities.KUNAI.get(), x, y, z, level, stack, weapon);
        this.setBaseDamage(BASE_DAMAGE);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.KUNAI.get());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }
}
