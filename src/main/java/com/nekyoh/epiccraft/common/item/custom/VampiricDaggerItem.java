package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.util.CombatUtil;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.joml.Vector3f;

/** Vampiric Dagger (drop do Vampire King): rouba 30% do dano causado como vida. */
public class VampiricDaggerItem extends DaggerItem {
    private static final DustParticleOptions BLOOD = new DustParticleOptions(new Vector3f(0.7F, 0.0F, 0.05F), 1.2F);

    public VampiricDaggerItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level) {
            float heal = CombatUtil.currentAttackDamage(attacker) * 0.3F;
            if (heal > 0.0F && attacker.getHealth() < attacker.getMaxHealth()) {
                attacker.heal(heal);
                level.sendParticles(BLOOD, target.getX(), target.getY(0.6D), target.getZ(), 10, 0.25D, 0.3D, 0.25D, 0.0D);
                level.sendParticles(BLOOD, attacker.getX(), attacker.getY(0.6D), attacker.getZ(), 6, 0.25D, 0.3D, 0.25D, 0.0D);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
