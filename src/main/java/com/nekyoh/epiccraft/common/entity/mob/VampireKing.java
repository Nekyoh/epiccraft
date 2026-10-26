package com.nekyoh.epiccraft.common.entity.mob;

import com.nekyoh.epiccraft.common.effect.ModEffects;
import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/**
 * Vampire King — chefe noturno.
 * - 200 de vida, barra de boss, um pouco maior que um jogador.
 * - Rouba vida a cada golpe e causa sangramento.
 * - "Dreno de sangue" à distância a cada ~8s.
 * - Se o alvo foge, se transforma em névoa e aparece atrás dele.
 * - Imune a sangramento. Na luz do sol fica fraco e lento (mas não queima).
 * - Dropa sempre a Vampiric Dagger e tem chance baixa de dropar a Philosofer Stone
 *   (loot table: data/epiccraft/loot_table/entities/vampire_king.json).
 */
public class VampireKing extends Monster {
    private static final DustParticleOptions BLOOD = new DustParticleOptions(new Vector3f(0.6F, 0.0F, 0.05F), 1.5F);

    private final ServerBossEvent bossEvent = (ServerBossEvent) new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10)
            .setDarkenScreen(true);

    private int drainCooldown = 160;
    private int blinkCooldown = 100;

    public VampireKing(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 60;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.SCALE, 1.15D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        // Segura a adaga só no visual; o drop vem da loot table (sempre)
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.VAMPIRIC_DAGGER.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        return data;
    }

    // ---------------- Combate ----------------

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living && this.level() instanceof ServerLevel level) {
            float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            this.heal(damage * 0.4F);
            living.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 100, 0, false, false, true), this);
            level.sendParticles(BLOOD, living.getX(), living.getY(0.6D), living.getZ(), 12, 0.3D, 0.4D, 0.3D, 0.0D);
        }
        return hit;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player) {
            this.setPersistenceRequired();
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return !effect.is(ModEffects.BLEEDING) && super.canBeAffected(effect);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        if (!(this.level() instanceof ServerLevel level)) return;

        // Fraco na luz do sol (não queima, para não virar boss que morre sozinho)
        if (this.tickCount % 40 == 0 && level.isDay() && level.canSeeSky(BlockPos.containing(this.getEyePosition()))) {
            this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, true, false));
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, true, false));
        }

        // Regenera devagar à noite
        if (this.tickCount % 60 == 0 && level.isNight() && this.getHealth() < this.getMaxHealth()) {
            this.heal(2.0F);
        }

        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) return;

        if (drainCooldown > 0) drainCooldown--;
        if (blinkCooldown > 0) blinkCooldown--;

        double distance = this.distanceTo(target);

        // Dreno de sangue à distância
        if (drainCooldown <= 0 && distance < 12.0D && this.hasLineOfSight(target)) {
            Vec3 from = target.position().add(0, target.getBbHeight() * 0.6D, 0);
            Vec3 to = this.getEyePosition();
            Vec3 step = to.subtract(from);
            int points = (int) (step.length() * 3);
            for (int i = 0; i <= points; i++) {
                Vec3 p = from.add(step.scale(i / (double) points));
                level.sendParticles(BLOOD, p.x, p.y, p.z, 2, 0.05D, 0.05D, 0.05D, 0.0D);
            }
            if (target.hurt(this.damageSources().indirectMagic(this, this), 5.0F)) {
                this.heal(5.0F);
            }
            level.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.EVOKER_PREPARE_ATTACK, SoundSource.HOSTILE, 1.0F, 0.6F);
            drainCooldown = 160 + this.random.nextInt(60);
        }

        // Vira névoa e aparece atrás do alvo
        if (blinkCooldown <= 0 && distance > 8.0D && distance < 32.0D) {
            Vec3 behind = target.position().subtract(target.getLookAngle().multiply(1, 0, 1).normalize().scale(2.0D));
            level.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(1.0D), this.getZ(), 30, 0.4D, 0.8D, 0.4D, 0.02D);
            if (this.randomTeleport(behind.x, behind.y, behind.z, true)) {
                level.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(1.0D), this.getZ(), 30, 0.4D, 0.8D, 0.4D, 0.02D);
                level.playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.PHANTOM_FLAP, SoundSource.HOSTILE, 1.5F, 0.6F);
            }
            blinkCooldown = 140 + this.random.nextInt(60);
        }
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

    // ---------------- Sons ----------------

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.EVOKER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.EVOKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.EVOKER_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return 0.6F;
    }
}
