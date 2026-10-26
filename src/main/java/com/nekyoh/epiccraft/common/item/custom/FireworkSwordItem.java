package com.nekyoh.epiccraft.common.item.custom;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Firework Sword: clique direito solta um foguete colorido na direção que você olha
 * (explode no alvo igual a besta com fogos). Os golpes soltam faíscas.
 */
public class FireworkSwordItem extends EpicSwordItem {
    private static final int COOLDOWN = 25;

    public FireworkSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            ItemStack rocket = randomRocket(level.random);
            FireworkRocketEntity entity = new FireworkRocketEntity(level, rocket, player,
                    player.getX(), player.getEyeY() - 0.15D, player.getZ(), true);
            Vec3 look = player.getLookAngle();
            entity.shoot(look.x, look.y, look.z, 1.8F, 1.0F);
            level.addFreshEntity(entity);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1.0F, 1.0F);
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        }
        player.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.FIREWORK, target.getX(), target.getY(0.6D), target.getZ(),
                    20, 0.3D, 0.3D, 0.3D, 0.15D);
            level.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.FIREWORK_ROCKET_TWINKLE, SoundSource.PLAYERS, 0.6F, 1.0F);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private static ItemStack randomRocket(RandomSource random) {
        IntList colors = new IntArrayList();
        int count = 1 + random.nextInt(3);
        for (int i = 0; i < count; i++) {
            colors.add(DyeColor.byId(random.nextInt(16)).getFireworkColor());
        }
        FireworkExplosion.Shape[] shapes = FireworkExplosion.Shape.values();
        FireworkExplosion explosion = new FireworkExplosion(
                shapes[random.nextInt(shapes.length)], colors, new IntArrayList(),
                random.nextBoolean(), random.nextBoolean());

        ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET);
        rocket.set(DataComponents.FIREWORKS, new Fireworks(1, List.of(explosion)));
        return rocket;
    }
}
