package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.entity.projectile.KunaiEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** Kunai: clique direito arremessa (empilha até 16). Também serve no corpo a corpo. */
public class KunaiItem extends Item implements ProjectileItem {

    public KunaiItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITCH_THROW, SoundSource.PLAYERS, 0.6F, 1.6F + level.random.nextFloat() * 0.2F);

        if (!level.isClientSide) {
            KunaiEntity kunai = new KunaiEntity(level, player, stack.copyWithCount(1));
            kunai.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.2F, 0.5F);
            if (player.getAbilities().instabuild) {
                kunai.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            level.addFreshEntity(kunai);
        }

        player.getCooldowns().addCooldown(this, 8);
        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        KunaiEntity kunai = new KunaiEntity(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        kunai.pickup = AbstractArrow.Pickup.ALLOWED;
        return kunai;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        EpicSwordItem.addAbilityTooltip(this, tooltip);
    }
}
