package com.nekyoh.epiccraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuração comum (config/epiccraft-common.toml).
 * Os valores são lidos direto com .get() quando necessário.
 */
public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_DEV_TAGS;

    public static final ModConfigSpec.DoubleValue DAGGER_DUAL_WIELD_BONUS;
    public static final ModConfigSpec.DoubleValue DAGGER_BACKSTAB_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue SWORD_BLOCK_REDUCTION;

    public static final ModConfigSpec.DoubleValue CREEPER_SWORD_CHANCE;
    public static final ModConfigSpec.DoubleValue CREEPER_SWORD_SELF_DAMAGE;

    static {
        BUILDER.push("general");
        SHOW_DEV_TAGS = BUILDER
                .comment("Habilita ou desabilita as tags de Desenvolvedor e Artista no TAB.")
                .define("showDevTags", true);
        BUILDER.pop();

        BUILDER.push("combat");
        DAGGER_DUAL_WIELD_BONUS = BUILDER
                .comment("Com uma adaga em cada mão: o golpe usa o dano da adaga mais forte",
                        "e soma (dano da principal + dano da secundária) * este valor.",
                        "0.5 = metade do dano somado das duas.")
                .defineInRange("daggerDualWieldBonus", 0.5D, 0.0D, 2.0D);
        DAGGER_BACKSTAB_MULTIPLIER = BUILDER
                .comment("Multiplicador de dano ao acertar alguém pelas costas com uma adaga.")
                .defineInRange("daggerBackstabMultiplier", 1.5D, 1.0D, 5.0D);
        SWORD_BLOCK_REDUCTION = BUILDER
                .comment("Quanto do dano frontal é bloqueado ao defender com katana/taco (estilo 1.8).",
                        "0.5 = bloqueia 50% do dano.")
                .defineInRange("swordBlockReduction", 0.5D, 0.0D, 1.0D);
        CREEPER_SWORD_CHANCE = BUILDER
                .comment("Chance (0-1) da Creeper Sword explodir ao acertar um golpe carregado.")
                .defineInRange("creeperSwordChance", 0.2D, 0.0D, 1.0D);
        CREEPER_SWORD_SELF_DAMAGE = BUILDER
                .comment("Dano que o jogador leva quando a Creeper Sword explode.")
                .defineInRange("creeperSwordSelfDamage", 2.0D, 0.0D, 20.0D);
        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    private Config() {}
}
