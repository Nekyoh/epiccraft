package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.util.AttackStrength;
import com.nekyoh.epiccraft.common.util.CombatUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

/**
 * Arma que defende estilo 1.8: segurando o botão direito ela reduz o dano frontal
 * (configurável, padrão 50%) em vez de virar um escudo completo. Não é desativada
 * por machado e não bloqueia 100% como antes.
 * A redução é aplicada em CombatEvents#onIncomingDamage.
 *
 * Se areaDamage = true, golpes carregados acertam inimigos em volta do alvo
 * (substitui o "sweep" padrão da espada).
 */
public class BlockingSwordItem extends EffectSwordItem {
    private final boolean areaDamage;

    public BlockingSwordItem(Tier tier, Item.Properties properties, boolean areaDamage, List<OnHit> effects) {
        super(tier, properties, effects);
        this.areaDamage = areaDamage;
    }

    public BlockingSwordItem(Tier tier, Item.Properties properties, boolean areaDamage) {
        this(tier, properties, areaDamage, List.of());
    }

    // ---------- BLOQUEIO ----------

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // Com um bloco na mão secundária (ex.: tocha), deixa colocar o bloco em vez de defender
        if (hand == InteractionHand.MAIN_HAND && player.getOffhandItem().getItem() instanceof BlockItem) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    /** O jogador está defendendo com esta arma há tempo suficiente? */
    public static boolean isBlocking(LivingEntity entity) {
        return entity.isUsingItem()
                && entity.getUseItem().getItem() instanceof BlockingSwordItem
                && entity.getTicksUsingItem() >= 3;
    }

    // ---------- DANO EM ÁREA ----------

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        // Com dano em área próprio, desligamos o sweep padrão para não duplicar
        if (areaDamage && itemAbility == ItemAbilities.SWORD_SWEEP) return false;
        return super.canPerformAction(stack, itemAbility);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (areaDamage && attacker.level() instanceof ServerLevel level
                && AttackStrength.isFull(attacker)
                && !attacker.isSprinting()
                && attacker.onGround()) {

            float sweepDamage = CombatUtil.currentAttackDamage(attacker) * 0.5F;
            for (LivingEntity other : level.getEntitiesOfClass(LivingEntity.class,
                    target.getBoundingBox().inflate(1.5D, 0.25D, 1.5D))) {
                if (other == target || !CombatUtil.canAreaHit(attacker, other)) continue;
                if (attacker.distanceToSqr(other) >= 12.0D) continue;

                other.knockback(0.4F, attacker.getX() - other.getX(), attacker.getZ() - other.getZ());
                other.hurt(CombatUtil.meleeSource(attacker), sweepDamage);
            }

            double dx = -Math.sin(Math.toRadians(attacker.getYRot()));
            double dz = Math.cos(Math.toRadians(attacker.getYRot()));
            level.sendParticles(ParticleTypes.SWEEP_ATTACK,
                    attacker.getX() + dx, attacker.getY(0.5D), attacker.getZ() + dz,
                    0, dx, 0.0D, dz, 0.0D);
            level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
