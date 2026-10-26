package com.nekyoh.epiccraft.common.entity.mob;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

/**
 * King Slime — slime gigante (tamanho 8, o dobro do maior slime normal).
 * - 160 de vida, barra de boss verde.
 * - Ao cair de um pulo esmaga tudo em volta (dano + arremesso para cima).
 * - Ao morrer se divide em 4 slimes grandes normais.
 */
public class KingSlime extends Slime {
    public static final int KING_SIZE = 8;

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_6);
    private boolean wasOnGroundKing = true;

    public KingSlime(EntityType<? extends Slime> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 160.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    /** Spawn natural: só no escuro e fora do pacífico. */
    public static boolean checkKingSlimeSpawnRules(EntityType<KingSlime> type, ServerLevelAccessor level,
                                                   MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Monster.isDarkEnoughToSpawn(level, pos, random)
                && checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public void setSize(int size, boolean resetHealth) {
        super.setSize(size, resetHealth);
        // O Slime recalcula os atributos pelo tamanho; o rei usa valores próprios
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(160.0D);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }
        this.xpReward = 60;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.setSize(KING_SIZE, true);
        return data;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel level) {
            boolean onGround = this.onGround();
            if (onGround && !this.wasOnGroundKing && this.isAlive()) {
                slam(level);
            }
            this.wasOnGroundKing = onGround;
        }
    }

    /** Pancada ao cair no chão. */
    private void slam(ServerLevel level) {
        double radius = this.getBbWidth() * 0.5D + 2.5D;
        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5D, 1.0D, 2.5D))) {
            if (victim == this || victim instanceof Slime || !victim.isAlive()) continue;
            if (victim instanceof Player player && (player.isCreative() || player.isSpectator())) continue;
            if (victim.distanceTo(this) > radius) continue;
            if (victim.hurt(this.damageSources().mobAttack(this), 6.0F)) {
                double resist = 1.0D - victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                victim.push(0.0D, 0.7D * resist, 0.0D);
                victim.knockback(0.8D, this.getX() - victim.getX(), this.getZ() - victim.getZ());
                victim.hurtMarked = true;
            }
        }
        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SLIME_BALL)),
                this.getX(), this.getY() + 0.2D, this.getZ(), 60, this.getBbWidth() * 0.4D, 0.1D, this.getBbWidth() * 0.4D, 0.15D);
        level.playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.SLIME_SQUISH, SoundSource.HOSTILE, 2.0F, 0.5F);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player) {
            this.setPersistenceRequired();
        }
        return super.hurt(source, amount);
    }

    /**
     * Ao morrer o Slime normal se divide em cópias do MESMO tipo (viraria 4 reis!).
     * Aqui criamos slimes normais e depois deixamos o super achar que é tamanho 1.
     */
    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide && this.isDeadOrDying() && this.level() instanceof ServerLevel level) {
            for (int i = 0; i < 4; i++) {
                Slime child = EntityType.SLIME.create(level);
                if (child == null) continue;
                float dx = ((i % 2) - 0.5F) * 2.0F;
                float dz = ((i / 2) - 0.5F) * 2.0F;
                if (this.isPersistenceRequired()) child.setPersistenceRequired();
                child.setSize(3, true);
                child.moveTo(this.getX() + dx, this.getY() + 0.5D, this.getZ() + dz, this.random.nextFloat() * 360.0F, 0.0F);
                level.addFreshEntity(child);
            }
            super.setSize(1, false);
        }
        super.remove(reason);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return this.getType().getDefaultLootTable();
    }

    @Override
    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }

    // ---------------- Boss bar ----------------

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }
}
