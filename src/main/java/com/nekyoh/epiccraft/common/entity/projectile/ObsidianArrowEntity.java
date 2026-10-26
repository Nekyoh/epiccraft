package com.nekyoh.epiccraft.common.entity.projectile;

import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/** Flecha de obsidiana: dano base alto e empurrão extra. */
public class ObsidianArrowEntity extends AbstractArrow {
    private static final double BASE_DAMAGE = 3.0D;   // flecha normal = 2.0
    private static final double EXTRA_KNOCKBACK = 0.6D;

    public ObsidianArrowEntity(EntityType<? extends ObsidianArrowEntity> type, Level level) {
        super(type, level);
        this.setBaseDamage(BASE_DAMAGE);
    }

    public ObsidianArrowEntity(Level level, LivingEntity shooter, ItemStack pickup, @Nullable ItemStack weapon) {
        super(ModEntities.OBSIDIAN_ARROW.get(), shooter, level, pickup, weapon);
        this.setBaseDamage(BASE_DAMAGE);
    }

    public ObsidianArrowEntity(Level level, double x, double y, double z, ItemStack pickup, @Nullable ItemStack weapon) {
        super(ModEntities.OBSIDIAN_ARROW.get(), x, y, z, level, pickup, weapon);
        this.setBaseDamage(BASE_DAMAGE);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Vec3 motion = this.getDeltaMovement();
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity living && living.isAlive()) {
            Vec3 push = motion.multiply(1.0D, 0.0D, 1.0D);
            if (push.lengthSqr() > 1.0E-4D) {
                double resist = Math.max(0.0D, 1.0D - living.getAttributeValue(
                        net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE));
                push = push.normalize().scale(EXTRA_KNOCKBACK * resist);
                living.push(push.x, 0.1D, push.z);
            }
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.OBSIDIAN_ARROW.get());
    }
}
