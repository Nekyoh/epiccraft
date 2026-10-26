package com.nekyoh.epiccraft.common.item.properties;

import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

/**
 * Tiers no formato 1.21:
 * SimpleTier(blocos que NÃO dropa, usos, velocidade de mineração, bônus de dano, encantabilidade, reparo)
 *
 * O "nível" antigo (0-4) virou a tag de blocos incorretos:
 *   0 = INCORRECT_FOR_WOODEN_TOOL, 1 = STONE, 2 = IRON, 3 = DIAMOND, 4 = NETHERITE
 */
public final class ModTiers {
    // --- Materiais originais ---
    public static final Tier EMERALD = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(Items.EMERALD));
    public static final Tier AMETHYST = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1700, 8.0F, 4.0F, 15, () -> Ingredient.of(Items.AMETHYST_CLUSTER));
    public static final Tier RUBY = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1800, 9.0F, 5.0F, 16, () -> Ingredient.of(ModItems.RUBY.get()));
    public static final Tier SAPHIRE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1800, 9.0F, 5.0F, 16, () -> Ingredient.of(ModItems.SAPHIRE.get()));
    public static final Tier NEZONITA = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2800, 10.0F, 12.0F, 20, () -> Ingredient.of(ModItems.NEZONITA_INGOT.get()));
    public static final Tier TEKYNITA = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2800, 20.0F, 8.0F, 20, () -> Ingredient.of(ModItems.TEKYNITA_INGOT.get()));
    public static final Tier STRAW = new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 20, 5.0F, 0.5F, 5, () -> Ingredient.of(ModItems.STRAW.get()));
    public static final Tier GLASS = new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1, 5.0F, 3.0F, 10, () -> Ingredient.EMPTY);
    public static final Tier COPPER = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 190, 5.0F, 1.0F, 13, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier ROSE = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 150, 6.0F, 2.0F, 13, () -> Ingredient.of(Items.ROSE_BUSH));
    public static final Tier PINK_IRON = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(ModItems.PINK_IRON_INGOT.get()));

    // --- Armas novas ---
    public static final Tier BLAZE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 15, () -> Ingredient.of(Items.BLAZE_ROD));
    public static final Tier CREEPER = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 900, 8.0F, 3.0F, 12, () -> Ingredient.of(Items.GUNPOWDER));
    public static final Tier ENDER = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1100, 8.0F, 3.0F, 15, () -> Ingredient.of(Items.ENDER_PEARL));
    public static final Tier FIREWORK = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 700, 6.0F, 2.0F, 14, () -> Ingredient.of(Items.PAPER));
    public static final Tier GUARDIAN = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1600, 8.0F, 4.0F, 15, () -> Ingredient.of(Items.PRISMARINE_CRYSTALS));
    public static final Tier WARDEN = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2600, 9.0F, 5.0F, 18, () -> Ingredient.of(Items.ECHO_SHARD));
    public static final Tier MONARCH = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3200, 10.0F, 6.0F, 22, () -> Ingredient.of(Items.NETHER_STAR));
    public static final Tier SNAKE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 14, () -> Ingredient.of(Items.SPIDER_EYE));
    public static final Tier WITHER_ROSE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 900, 8.0F, 3.0F, 14, () -> Ingredient.of(Items.WITHER_ROSE));
    public static final Tier DARK = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1800, 8.0F, 3.0F, 15, () -> Ingredient.of(ModItems.OBSIDIAN_SHARD.get()));
    public static final Tier JET = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1300, 8.0F, 3.0F, 14, () -> Ingredient.of(Items.IRON_INGOT));
    public static final Tier VAMPIRIC = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1400, 8.0F, 3.0F, 18, () -> Ingredient.of(Items.GHAST_TEAR));
    public static final Tier INVENTOR = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1200, 8.0F, 3.0F, 15, () -> Ingredient.of(Items.COPPER_INGOT));

    private ModTiers() {}
}
