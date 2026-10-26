package com.nekyoh.epiccraft.common.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class CombatUtil {

    /** Fonte de dano corpo a corpo correta para jogador ou mob. */
    public static DamageSource meleeSource(LivingEntity attacker) {
        return attacker instanceof Player player
                ? attacker.damageSources().playerAttack(player)
                : attacker.damageSources().mobAttack(attacker);
    }

    /** Dano aproximado do golpe atual levando em conta a barra de ataque. */
    public static float currentAttackDamage(LivingEntity attacker) {
        float strength = AttackStrength.get(attacker);
        float base = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return base * (0.2F + strength * strength * 0.8F);
    }

    /** Alvo válido para dano em área (não acerta o próprio jogador, aliados, pets ou suportes). */
    public static boolean canAreaHit(LivingEntity attacker, LivingEntity target) {
        if (target == attacker || !target.isAlive() || target.isSpectator()) return false;
        if (target instanceof ArmorStand) return false;
        if (attacker.isAlliedTo(target)) return false;
        if (target instanceof TamableAnimal pet && pet.isOwnedBy(attacker)) return false;
        return !(target instanceof Player p && (p.isCreative() || p.isSpectator()));
    }

    /** true se 'attacker' está atrás de 'target'. */
    public static boolean isBehind(LivingEntity target, LivingEntity attacker) {
        Vec3 look = target.getViewVector(1.0F).multiply(1, 0, 1);
        Vec3 toAttacker = attacker.position().subtract(target.position()).multiply(1, 0, 1);
        if (look.lengthSqr() < 1.0E-4 || toAttacker.lengthSqr() < 1.0E-4) return false;
        return look.normalize().dot(toAttacker.normalize()) < -0.35D;
    }

    /** true se a posição está na frente de quem está olhando (cone de ~110°). */
    public static boolean isInFront(LivingEntity viewer, Vec3 position) {
        Vec3 look = viewer.getViewVector(1.0F).multiply(1, 0, 1);
        Vec3 to = position.subtract(viewer.position()).multiply(1, 0, 1);
        if (look.lengthSqr() < 1.0E-4 || to.lengthSqr() < 1.0E-4) return true;
        return look.normalize().dot(to.normalize()) > 0.35D;
    }

    private CombatUtil() {}
}
