package com.nekyoh.epiccraft.common.item;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.enchantment.ModEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.nekyoh.epiccraft.common.item.ModItems.*;

public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpicCraft.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EPIC_CRAFT_TAB_COMBAT = CREATIVE_MODE_TABS.register("epic_craft_tab_combat",
            () -> CreativeModeTab.builder()
                    .icon(() -> NEZONITA_SCYTHE.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.epiccraft.combat"))
                    .displayItems((parameters, output) -> {
                        // Espadas
                        output.accept(COPPER_SWORD);
                        output.accept(PINK_IRON_SWORD);
                        output.accept(EMERALD_SWORD);
                        output.accept(RUBY_SWORD);
                        output.accept(SAPHIRE_SWORD);
                        output.accept(AMETHYST_SWORD);
                        output.accept(NEZONITA_SWORD);
                        output.accept(TEKYNITA_SWORD);
                        output.accept(STRAW_SWORD);
                        output.accept(GLASS_SWORD);
                        output.accept(ROSE_BUSH_SWORD);

                        // Armas especiais
                        output.accept(BLAZE_SWORD);
                        output.accept(CREEPER_SWORD);
                        output.accept(ENDERMAN_SWORD);
                        output.accept(FIREWORK_SWORD);
                        output.accept(SNAKE_SWORD);
                        output.accept(WITHER_ROSE_SWORD);
                        output.accept(GUARDIAN_SWORD);
                        output.accept(WARDEN_SWORD);
                        output.accept(MONARCH_SWORD);
                        output.accept(KATANA);
                        output.accept(DARK_KATANA);
                        output.accept(JET);
                        output.accept(KUNAI);
                        output.accept(INVENTOR_WRENCH);

                        // Machados
                        output.accept(COPPER_AXE);
                        output.accept(PINK_IRON_AXE);
                        output.accept(EMERALD_AXE);
                        output.accept(RUBY_AXE);
                        output.accept(SAPHIRE_AXE);
                        output.accept(AMETHYST_AXE);
                        output.accept(NEZONITA_AXE);
                        output.accept(TEKYNITA_AXE);

                        // Adagas
                        output.accept(WOODEN_DAGGER);
                        output.accept(STONE_DAGGER);
                        output.accept(IRON_DAGGER);
                        output.accept(COPPER_DAGGER);
                        output.accept(GOLDEN_DAGGER);
                        output.accept(DIAMOND_DAGGER);
                        output.accept(NETHERITE_DAGGER);
                        output.accept(NEZONITA_DAGGER);
                        output.accept(TEKYNITA_DAGGER);
                        output.accept(VAMPIRIC_DAGGER);

                        output.accept(NEZONITA_SCYTHE);
                        output.accept(MJOLNIR);
                        output.accept(BRUTE_BATTLE_AXE);
                        output.accept(FRYING_PAN);
                        output.accept(BASEBALL_BAT);
                        output.accept(UMBRELLA);

                        output.accept(OBSIDIAN_ARROW);

                        // Armaduras
                        output.accept(COPPER_HELMET);
                        output.accept(COPPER_CHESTPLATE);
                        output.accept(COPPER_LEGGINGS);
                        output.accept(COPPER_BOOTS);
                        output.accept(PINK_IRON_HELMET);
                        output.accept(PINK_IRON_CHESTPLATE);
                        output.accept(PINK_IRON_LEGGINGS);
                        output.accept(PINK_IRON_BOOTS);
                        output.accept(EMERALD_HELMET);
                        output.accept(EMERALD_CHESTPLATE);
                        output.accept(EMERALD_LEGGINGS);
                        output.accept(EMERALD_BOOTS);
                        output.accept(RUBY_HELMET);
                        output.accept(RUBY_CHESTPLATE);
                        output.accept(RUBY_LEGGINGS);
                        output.accept(RUBY_BOOTS);
                        output.accept(SAPHIRE_HELMET);
                        output.accept(SAPHIRE_CHESTPLATE);
                        output.accept(SAPHIRE_LEGGINGS);
                        output.accept(SAPHIRE_BOOTS);
                        output.accept(AMETHYST_HELMET);
                        output.accept(AMETHYST_CHESTPLATE);
                        output.accept(AMETHYST_LEGGINGS);
                        output.accept(AMETHYST_BOOTS);
                        output.accept(NEZONITA_HELMET);
                        output.accept(NEZONITA_CHESTPLATE);
                        output.accept(NEZONITA_LEGGINGS);
                        output.accept(NEZONITA_BOOTS);
                        output.accept(TEKYNITA_HELMET);
                        output.accept(TEKYNITA_CHESTPLATE);
                        output.accept(TEKYNITA_LEGGINGS);
                        output.accept(TEKYNITA_BOOTS);

                        // Livros do Emerald Grace (encantamento data-driven na 1.21)
                        ModEnchantments.get(parameters.holders(), ModEnchantments.EMERALD_GRACE).ifPresent(holder -> {
                            output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holder, 1)));
                            output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holder, 2)));
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EPIC_CRAFT_TAB_TOOLS = CREATIVE_MODE_TABS.register("epic_craft_tab_tools",
            () -> CreativeModeTab.builder()
                    .icon(() -> TEKYNITA_PICKAXE.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.epiccraft.tools"))
                    .displayItems((parameters, output) -> {
                        output.accept(COPPER_PICKAXE);
                        output.accept(COPPER_AXE);
                        output.accept(COPPER_SHOVEL);
                        output.accept(COPPER_HOE);
                        output.accept(PINK_IRON_PICKAXE);
                        output.accept(PINK_IRON_AXE);
                        output.accept(PINK_IRON_SHOVEL);
                        output.accept(PINK_IRON_HOE);
                        output.accept(EMERALD_PICKAXE);
                        output.accept(EMERALD_AXE);
                        output.accept(EMERALD_SHOVEL);
                        output.accept(EMERALD_HOE);
                        output.accept(RUBY_PICKAXE);
                        output.accept(RUBY_AXE);
                        output.accept(RUBY_SHOVEL);
                        output.accept(RUBY_HOE);
                        output.accept(SAPHIRE_PICKAXE);
                        output.accept(SAPHIRE_AXE);
                        output.accept(SAPHIRE_SHOVEL);
                        output.accept(SAPHIRE_HOE);
                        output.accept(AMETHYST_PICKAXE);
                        output.accept(AMETHYST_AXE);
                        output.accept(AMETHYST_SHOVEL);
                        output.accept(AMETHYST_HOE);
                        output.accept(NEZONITA_PICKAXE);
                        output.accept(NEZONITA_AXE);
                        output.accept(NEZONITA_SHOVEL);
                        output.accept(NEZONITA_HOE);
                        output.accept(TEKYNITA_PICKAXE);
                        output.accept(TEKYNITA_AXE);
                        output.accept(TEKYNITA_SHOVEL);
                        output.accept(TEKYNITA_HOE);

                        output.accept(BRUTE_BATTLE_AXE);
                        output.accept(CROWBAR);
                        output.accept(WRENCH);
                        output.accept(INVENTOR_WRENCH);
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EPIC_CRAFT_TAB_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("epic_craft_tab_natural_blocks",
            () -> CreativeModeTab.builder()
                    .icon(() -> TEKYNITA_ORE.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.epiccraft.natural_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(RUBY_ORE);
                        output.accept(DEEPSLATE_RUBY_ORE);
                        output.accept(SAPHIRE_ORE);
                        output.accept(DEEPSLATE_SAPHIRE_ORE);
                        output.accept(NEZONITA_ORE);
                        output.accept(TEKYNITA_ORE);
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EPIC_CRAFT_TAB_BUILDING_BLOCKS = CREATIVE_MODE_TABS.register("epic_craft_tab_building_blocks",
            () -> CreativeModeTab.builder()
                    .icon(() -> TEKYNITA_BLOCK.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.epiccraft.building_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(CHISELED_EMERALD_BLOCK);
                        output.accept(PINK_IRON_BLOCK);
                        output.accept(RUBY_BLOCK);
                        output.accept(SAPHIRE_BLOCK);
                        output.accept(NEZONITA_BLOCK);
                        output.accept(TEKYNITA_BLOCK);
                        output.accept(COPPER_CHAIN);
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EPIC_CRAFT_TAB_MISC = CREATIVE_MODE_TABS.register("epic_craft_tab_misc",
            () -> CreativeModeTab.builder()
                    .icon(() -> QUARTZ_STICK.get().getDefaultInstance())
                    .title(Component.translatable("creativetab.epiccraft.misc"))
                    .displayItems((parameters, output) -> {
                        output.accept(PINK_IRON_INGOT);
                        output.accept(QUARTZ_STICK);
                        output.accept(NETHERITE_STICK);
                        output.accept(OBSIDIAN_SHARD);
                        output.accept(COPPER_NUGGET);
                        output.accept(STRAW);
                        output.accept(RUBY);
                        output.accept(SAPHIRE);
                        output.accept(CHAIN_MESH);
                        output.accept(NEZONITA_INGOT);
                        output.accept(TEKYNITA_INGOT);
                        output.accept(NEZONITA_CRYSTAL);
                        output.accept(TEKYNITA_CRYSTAL);
                        output.accept(PHILOSOFER_STONE);
                        output.accept(BASS);

                        output.accept(THE_INVENTOR_SPAWN_EGG);
                        output.accept(VAMPIRE_KING_SPAWN_EGG);
                        output.accept(KING_SLIME_SPAWN_EGG);
                    }).build());

    private ModTabs() {}
}
