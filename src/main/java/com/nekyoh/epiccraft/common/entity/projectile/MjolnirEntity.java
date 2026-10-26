package com.nekyoh.epiccraft.common.entity.projectile;

import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Martelo arremessado. Antes ele estendia ThrownTrident e "mentia" o tipo de entidade,
 * o que bugava a sincronização. Agora é um projétil próprio com retorno igual Lealdade.
 */
public class MjolnirEntity extends AbstractArrow {
    private static final float THROW_DAMAGE = 12.0F;
    private boolean dealtDamage;
    private int returnTicks;

    public MjolnirEntity(EntityType<? extends MjolnirEntity> type, Level level) {
        super(type, level);
    }

    public MjolnirEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.MJOLNIR.get(), owner, level, stack, null);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.MJOLNIR.get());
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!isAcceptableReturnOwner(owner)) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                // Volta para o dono (mesma lógica da Lealdade nível 3)
                this.setNoPhysics(true);
                Vec3 toOwner = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + toOwner.y * 0.045D, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(toOwner.normalize().scale(0.15D)));
                if (this.returnTicks == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 0.8F);
                }
                this.returnTicks++;
            }
        }
        super.tick();
    }

    private static boolean isAcceptableReturnOwner(Entity owner) {
        if (!owner.isAlive()) return false;
        return !(owner instanceof ServerPlayer player) || !player.isSpectator();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        DamageSource source = this.damageSources().trident(this, owner == null ? this : owner);
        this.dealtDamage = true;

        if (target.hurt(source, THROW_DAMAGE) && target instanceof LivingEntity living
                && this.level() instanceof ServerLevel serverLevel) {
            this.doKnockback(living, source);
            this.doPostHurtEffects(living);
            strikeLightning(serverLevel, target.position());
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 0.8F);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.level() instanceof ServerLevel serverLevel && !this.dealtDamage) {
            strikeLightning(serverLevel, result.getLocation());
        }
    }

    private void strikeLightning(ServerLevel level, Vec3 pos) {
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(pos);
            if (this.getOwner() instanceof ServerPlayer player) {
                bolt.setCause(player);
            }
            level.addFreshEntity(bolt);
        }
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player)
                || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}
