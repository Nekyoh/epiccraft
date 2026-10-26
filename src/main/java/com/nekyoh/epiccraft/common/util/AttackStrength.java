package com.nekyoh.epiccraft.common.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * O Minecraft zera a barra de ataque ANTES de chamar hurtEnemy(), então dentro
 * do item a força do golpe sempre parece 0 (era por isso que o dano em área da
 * katana nunca funcionava). Guardamos aqui a força no início do ataque
 * (AttackEntityEvent) para os itens consultarem depois.
 */
public final class AttackStrength {
    private static final Map<Player, Float> LAST = Collections.synchronizedMap(new WeakHashMap<>());

    public static void record(Player player) {
        LAST.put(player, player.getAttackStrengthScale(0.5F));
    }

    /** Força do último golpe (0..1). Mobs sempre contam como golpe cheio. */
    public static float get(LivingEntity entity) {
        if (entity instanceof Player player) {
            Float value = LAST.get(player);
            return value == null ? 1.0F : value;
        }
        return 1.0F;
    }

    public static boolean isFull(LivingEntity entity) {
        return get(entity) > 0.9F;
    }

    private AttackStrength() {}
}
