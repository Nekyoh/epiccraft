package com.nekyoh.epiccraft.common.item;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.block.ModBlocks;
import com.nekyoh.epiccraft.common.effect.ModEffects;
import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.item.custom.*;
import com.nekyoh.epiccraft.common.item.custom.EffectSwordItem.OnHit;
import com.nekyoh.epiccraft.common.item.properties.ModArmorMaterials;
import com.nekyoh.epiccraft.common.item.properties.ModTiers;
import com.nekyoh.epiccraft.common.util.ItemAttributes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EpicCraft.MODID);

    // =====================================================================
    // EMERALD
    // =====================================================================
    public static final DeferredItem<SwordItem> EMERALD_SWORD = sword("emerald_sword", ModTiers.EMERALD, 4, -2.4F, false);
    public static final DeferredItem<PickaxeItem> EMERALD_PICKAXE = pickaxe("emerald_pickaxe", ModTiers.EMERALD, 1, -2.8F, false);
    public static final DeferredItem<AxeItem> EMERALD_AXE = axe("emerald_axe", ModTiers.EMERALD, 5.0F, -3.0F, false);
    public static final DeferredItem<ShovelItem> EMERALD_SHOVEL = shovel("emerald_shovel", ModTiers.EMERALD, 1.5F, -3.0F, false);
    public static final DeferredItem<HoeItem> EMERALD_HOE = hoe("emerald_hoe", ModTiers.EMERALD, -4, 0.0F, false);
    public static final DeferredItem<ArmorItem> EMERALD_HELMET = armor("emerald_helmet", ModArmorMaterials.EMERALD, ArmorItem.Type.HELMET, ModArmorMaterials.EMERALD_DURABILITY, false, "emerald");
    public static final DeferredItem<ArmorItem> EMERALD_CHESTPLATE = armor("emerald_chestplate", ModArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.EMERALD_DURABILITY, false, "emerald");
    public static final DeferredItem<ArmorItem> EMERALD_LEGGINGS = armor("emerald_leggings", ModArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS, ModArmorMaterials.EMERALD_DURABILITY, false, "emerald");
    public static final DeferredItem<ArmorItem> EMERALD_BOOTS = armor("emerald_boots", ModArmorMaterials.EMERALD, ArmorItem.Type.BOOTS, ModArmorMaterials.EMERALD_DURABILITY, false, "emerald");

    // =====================================================================
    // RUBY
    // =====================================================================
    public static final DeferredItem<SwordItem> RUBY_SWORD = sword("ruby_sword", ModTiers.RUBY, 5, -2.4F, false);
    public static final DeferredItem<PickaxeItem> RUBY_PICKAXE = pickaxe("ruby_pickaxe", ModTiers.RUBY, 1, -2.8F, false);
    public static final DeferredItem<AxeItem> RUBY_AXE = axe("ruby_axe", ModTiers.RUBY, 5.0F, -3.0F, false);
    public static final DeferredItem<ShovelItem> RUBY_SHOVEL = shovel("ruby_shovel", ModTiers.RUBY, 1.5F, -3.0F, false);
    public static final DeferredItem<HoeItem> RUBY_HOE = hoe("ruby_hoe", ModTiers.RUBY, -4, 0.0F, false);
    public static final DeferredItem<ArmorItem> RUBY_HELMET = armor("ruby_helmet", ModArmorMaterials.RUBY, ArmorItem.Type.HELMET, ModArmorMaterials.RUBY_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> RUBY_CHESTPLATE = armor("ruby_chestplate", ModArmorMaterials.RUBY, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.RUBY_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> RUBY_LEGGINGS = armor("ruby_leggings", ModArmorMaterials.RUBY, ArmorItem.Type.LEGGINGS, ModArmorMaterials.RUBY_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> RUBY_BOOTS = armor("ruby_boots", ModArmorMaterials.RUBY, ArmorItem.Type.BOOTS, ModArmorMaterials.RUBY_DURABILITY, false, null);

    // =====================================================================
    // SAPHIRE
    // =====================================================================
    public static final DeferredItem<SwordItem> SAPHIRE_SWORD = sword("saphire_sword", ModTiers.SAPHIRE, 5, -2.4F, false);
    public static final DeferredItem<PickaxeItem> SAPHIRE_PICKAXE = pickaxe("saphire_pickaxe", ModTiers.SAPHIRE, 1, -2.8F, false);
    public static final DeferredItem<AxeItem> SAPHIRE_AXE = axe("saphire_axe", ModTiers.SAPHIRE, 5.0F, -3.0F, false);
    public static final DeferredItem<ShovelItem> SAPHIRE_SHOVEL = shovel("saphire_shovel", ModTiers.SAPHIRE, 1.5F, -3.0F, false);
    public static final DeferredItem<HoeItem> SAPHIRE_HOE = hoe("saphire_hoe", ModTiers.SAPHIRE, -4, 0.0F, false);
    public static final DeferredItem<ArmorItem> SAPHIRE_HELMET = armor("saphire_helmet", ModArmorMaterials.SAPHIRE, ArmorItem.Type.HELMET, ModArmorMaterials.SAPHIRE_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> SAPHIRE_CHESTPLATE = armor("saphire_chestplate", ModArmorMaterials.SAPHIRE, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.SAPHIRE_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> SAPHIRE_LEGGINGS = armor("saphire_leggings", ModArmorMaterials.SAPHIRE, ArmorItem.Type.LEGGINGS, ModArmorMaterials.SAPHIRE_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> SAPHIRE_BOOTS = armor("saphire_boots", ModArmorMaterials.SAPHIRE, ArmorItem.Type.BOOTS, ModArmorMaterials.SAPHIRE_DURABILITY, false, null);

    // =====================================================================
    // AMETHYST
    // =====================================================================
    public static final DeferredItem<SwordItem> AMETHYST_SWORD = sword("amethyst_sword", ModTiers.AMETHYST, 6, -2.4F, false);
    public static final DeferredItem<PickaxeItem> AMETHYST_PICKAXE = pickaxe("amethyst_pickaxe", ModTiers.AMETHYST, 1, -2.8F, false);
    public static final DeferredItem<AxeItem> AMETHYST_AXE = axe("amethyst_axe", ModTiers.AMETHYST, 5.0F, -3.0F, false);
    public static final DeferredItem<ShovelItem> AMETHYST_SHOVEL = shovel("amethyst_shovel", ModTiers.AMETHYST, 1.5F, -3.0F, false);
    public static final DeferredItem<HoeItem> AMETHYST_HOE = hoe("amethyst_hoe", ModTiers.AMETHYST, -4, 0.0F, false);
    public static final DeferredItem<ArmorItem> AMETHYST_HELMET = armor("amethyst_helmet", ModArmorMaterials.AMETHYST, ArmorItem.Type.HELMET, ModArmorMaterials.AMETHYST_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> AMETHYST_CHESTPLATE = armor("amethyst_chestplate", ModArmorMaterials.AMETHYST, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.AMETHYST_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> AMETHYST_LEGGINGS = armor("amethyst_leggings", ModArmorMaterials.AMETHYST, ArmorItem.Type.LEGGINGS, ModArmorMaterials.AMETHYST_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> AMETHYST_BOOTS = armor("amethyst_boots", ModArmorMaterials.AMETHYST, ArmorItem.Type.BOOTS, ModArmorMaterials.AMETHYST_DURABILITY, false, null);

    // =====================================================================
    // NEZONITA
    // =====================================================================
    public static final DeferredItem<SwordItem> NEZONITA_SWORD = sword("nezonita_sword", ModTiers.NEZONITA, 10, -2.4F, true);
    public static final DeferredItem<PickaxeItem> NEZONITA_PICKAXE = pickaxe("nezonita_pickaxe", ModTiers.NEZONITA, 1, -2.8F, true);
    public static final DeferredItem<AxeItem> NEZONITA_AXE = axe("nezonita_axe", ModTiers.NEZONITA, 5.0F, -3.0F, true);
    public static final DeferredItem<ShovelItem> NEZONITA_SHOVEL = shovel("nezonita_shovel", ModTiers.NEZONITA, 1.5F, -3.0F, true);
    public static final DeferredItem<HoeItem> NEZONITA_HOE = hoe("nezonita_hoe", ModTiers.NEZONITA, -4, 0.0F, true);
    public static final DeferredItem<DaggerItem> NEZONITA_DAGGER = dagger("nezonita_dagger", ModTiers.NEZONITA, true);
    public static final DeferredItem<SwordItem> NEZONITA_SCYTHE = sword("nezonita_scythe", ModTiers.NEZONITA, 10, -2.4F, true);
    public static final DeferredItem<ArmorItem> NEZONITA_HELMET = armor("nezonita_helmet", ModArmorMaterials.NEZONITA, ArmorItem.Type.HELMET, ModArmorMaterials.NEZONITA_DURABILITY, true, "nezonita");
    public static final DeferredItem<ArmorItem> NEZONITA_CHESTPLATE = armor("nezonita_chestplate", ModArmorMaterials.NEZONITA, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.NEZONITA_DURABILITY, true, "nezonita");
    public static final DeferredItem<ArmorItem> NEZONITA_LEGGINGS = armor("nezonita_leggings", ModArmorMaterials.NEZONITA, ArmorItem.Type.LEGGINGS, ModArmorMaterials.NEZONITA_DURABILITY, true, "nezonita");
    public static final DeferredItem<ArmorItem> NEZONITA_BOOTS = armor("nezonita_boots", ModArmorMaterials.NEZONITA, ArmorItem.Type.BOOTS, ModArmorMaterials.NEZONITA_DURABILITY, true, "nezonita");

    // =====================================================================
    // TEKYNITA
    // =====================================================================
    // Antes estava com velocidade +5 (sem cooldown nenhum), provavelmente copiado da katana comentada.
    public static final DeferredItem<SwordItem> TEKYNITA_SWORD = sword("tekynita_sword", ModTiers.TEKYNITA, 8, -2.0F, true);
    public static final DeferredItem<PickaxeItem> TEKYNITA_PICKAXE = pickaxe("tekynita_pickaxe", ModTiers.TEKYNITA, 1, -2.8F, true);
    public static final DeferredItem<AxeItem> TEKYNITA_AXE = axe("tekynita_axe", ModTiers.TEKYNITA, 5.0F, -3.0F, true);
    public static final DeferredItem<ShovelItem> TEKYNITA_SHOVEL = shovel("tekynita_shovel", ModTiers.TEKYNITA, 1.5F, -3.0F, true);
    public static final DeferredItem<HoeItem> TEKYNITA_HOE = hoe("tekynita_hoe", ModTiers.TEKYNITA, -4, 0.0F, true);
    public static final DeferredItem<DaggerItem> TEKYNITA_DAGGER = dagger("tekynita_dagger", ModTiers.TEKYNITA, true);
    public static final DeferredItem<ArmorItem> TEKYNITA_HELMET = armor("tekynita_helmet", ModArmorMaterials.TEKYNITA, ArmorItem.Type.HELMET, ModArmorMaterials.TEKYNITA_DURABILITY, true, "tekynita");
    public static final DeferredItem<ArmorItem> TEKYNITA_CHESTPLATE = armor("tekynita_chestplate", ModArmorMaterials.TEKYNITA, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.TEKYNITA_DURABILITY, true, "tekynita");
    public static final DeferredItem<ArmorItem> TEKYNITA_LEGGINGS = armor("tekynita_leggings", ModArmorMaterials.TEKYNITA, ArmorItem.Type.LEGGINGS, ModArmorMaterials.TEKYNITA_DURABILITY, true, "tekynita");
    public static final DeferredItem<ArmorItem> TEKYNITA_BOOTS = armor("tekynita_boots", ModArmorMaterials.TEKYNITA, ArmorItem.Type.BOOTS, ModArmorMaterials.TEKYNITA_DURABILITY, true, "tekynita");

    // =====================================================================
    // COPPER
    // =====================================================================
    public static final DeferredItem<SwordItem> COPPER_SWORD = sword("copper_sword", ModTiers.COPPER, 3, -2.4F, false);
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = pickaxe("copper_pickaxe", ModTiers.COPPER, 1, -2.8F, false);
    public static final DeferredItem<AxeItem> COPPER_AXE = axe("copper_axe", ModTiers.COPPER, 7.0F, -3.2F, false);
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = shovel("copper_shovel", ModTiers.COPPER, 1.5F, -3.0F, false);
    public static final DeferredItem<HoeItem> COPPER_HOE = hoe("copper_hoe", ModTiers.COPPER, -1, -2.0F, false);
    public static final DeferredItem<DaggerItem> COPPER_DAGGER = dagger("copper_dagger", ModTiers.COPPER, false);
    public static final DeferredItem<ArmorItem> COPPER_HELMET = armor("copper_helmet", ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, ModArmorMaterials.COPPER_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> COPPER_CHESTPLATE = armor("copper_chestplate", ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.COPPER_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> COPPER_LEGGINGS = armor("copper_leggings", ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, ModArmorMaterials.COPPER_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> COPPER_BOOTS = armor("copper_boots", ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, ModArmorMaterials.COPPER_DURABILITY, false, null);

    // =====================================================================
    // PINK IRON
    // =====================================================================
    public static final DeferredItem<SwordItem> PINK_IRON_SWORD = sword("pink_iron_sword", ModTiers.PINK_IRON, 3, -2.4F, false);
    public static final DeferredItem<PickaxeItem> PINK_IRON_PICKAXE = pickaxe("pink_iron_pickaxe", ModTiers.PINK_IRON, 1, -2.8F, false);
    public static final DeferredItem<AxeItem> PINK_IRON_AXE = axe("pink_iron_axe", ModTiers.PINK_IRON, 6.0F, -3.1F, false);
    public static final DeferredItem<ShovelItem> PINK_IRON_SHOVEL = shovel("pink_iron_shovel", ModTiers.PINK_IRON, 1.5F, -3.0F, false);
    public static final DeferredItem<HoeItem> PINK_IRON_HOE = hoe("pink_iron_hoe", ModTiers.PINK_IRON, -2, -1.0F, false);
    public static final DeferredItem<ArmorItem> PINK_IRON_HELMET = armor("pink_iron_helmet", ModArmorMaterials.PINK_IRON, ArmorItem.Type.HELMET, ModArmorMaterials.PINK_IRON_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> PINK_IRON_CHESTPLATE = armor("pink_iron_chestplate", ModArmorMaterials.PINK_IRON, ArmorItem.Type.CHESTPLATE, ModArmorMaterials.PINK_IRON_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> PINK_IRON_LEGGINGS = armor("pink_iron_leggings", ModArmorMaterials.PINK_IRON, ArmorItem.Type.LEGGINGS, ModArmorMaterials.PINK_IRON_DURABILITY, false, null);
    public static final DeferredItem<ArmorItem> PINK_IRON_BOOTS = armor("pink_iron_boots", ModArmorMaterials.PINK_IRON, ArmorItem.Type.BOOTS, ModArmorMaterials.PINK_IRON_DURABILITY, false, null);

    // =====================================================================
    // ADAGAS
    // =====================================================================
    public static final DeferredItem<DaggerItem> WOODEN_DAGGER = dagger("wooden_dagger", Tiers.WOOD, false);
    public static final DeferredItem<DaggerItem> STONE_DAGGER = dagger("stone_dagger", Tiers.STONE, false);
    public static final DeferredItem<DaggerItem> IRON_DAGGER = dagger("iron_dagger", Tiers.IRON, false);
    public static final DeferredItem<DaggerItem> GOLDEN_DAGGER = dagger("golden_dagger", Tiers.GOLD, false);
    public static final DeferredItem<DaggerItem> DIAMOND_DAGGER = dagger("diamond_dagger", Tiers.DIAMOND, false);
    public static final DeferredItem<DaggerItem> NETHERITE_DAGGER = dagger("netherite_dagger", Tiers.NETHERITE, true);

    public static final DeferredItem<VampiricDaggerItem> VAMPIRIC_DAGGER = ITEMS.register("vampiric_dagger",
            () -> new VampiricDaggerItem(ModTiers.VAMPIRIC, new Item.Properties()
                    .rarity(Rarity.RARE)
                    .attributes(SwordItem.createAttributes(ModTiers.VAMPIRIC, DaggerItem.BASE_DAMAGE + 1, DaggerItem.ATTACK_SPEED))));

    // =====================================================================
    // COMBATE (originais)
    // =====================================================================
    public static final DeferredItem<Item> OBSIDIAN_ARROW = ITEMS.register("obsidian_arrow",
            () -> new ObsidianArrowItem(new Item.Properties()));

    public static final DeferredItem<SwordItem> STRAW_SWORD = sword("straw_sword", ModTiers.STRAW, -1, 2.0F, false);

    public static final DeferredItem<GlassSwordItem> GLASS_SWORD = ITEMS.register("glass_sword",
            () -> new GlassSwordItem(ModTiers.GLASS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.GLASS, 2, 1.0F))));

    public static final DeferredItem<MjolnirItem> MJOLNIR = ITEMS.register("mjolnir",
            () -> new MjolnirItem(new Item.Properties()
                    .durability(2500)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .attributes(ItemAttributes.melee(8.0D, -2.9D))));

    public static final DeferredItem<BlockingSwordItem> KATANA = ITEMS.register("katana",
            () -> new BlockingSwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 8, -2.4F)), true));

    public static final DeferredItem<AxeItem> BRUTE_BATTLE_AXE = ITEMS.register("brute_battle_axe",
            () -> new AxeItem(Tiers.GOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.GOLD, 10.0F, -3.5F))));

    public static final DeferredItem<RoseBushSwordItem> ROSE_BUSH_SWORD = ITEMS.register("rose_bush_sword",
            () -> new RoseBushSwordItem(ModTiers.ROSE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.ROSE, 3, 2.0F))));

    public static final DeferredItem<FryingPanItem> FRYING_PAN = ITEMS.register("frying_pan",
            () -> new FryingPanItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 2, -2.4F))));

    public static final DeferredItem<CrowbarItem> CROWBAR = ITEMS.register("crowbar",
            () -> new CrowbarItem(new Item.Properties()
                    .durability(128)
                    .attributes(ItemAttributes.melee(4.0D, -2.4D))));

    public static final DeferredItem<BassItem> BASS = ITEMS.register("bass",
            () -> new BassItem(new Item.Properties()
                    .durability(620)
                    .attributes(ItemAttributes.melee(3.0D, -2.4D))));

    public static final DeferredItem<BlockingSwordItem> BASEBALL_BAT = ITEMS.register("baseball_bat",
            () -> new BlockingSwordItem(Tiers.WOOD, new Item.Properties()
                    .attributes(ItemAttributes.with(SwordItem.createAttributes(Tiers.WOOD, 3, -2.4F),
                            Attributes.ATTACK_KNOCKBACK, "baseball_bat_knockback", 1.0D, EquipmentSlotGroup.MAINHAND)),
                    false));

    // =====================================================================
    // ARMAS NOVAS
    // =====================================================================
    public static final DeferredItem<BlazeSwordItem> BLAZE_SWORD = ITEMS.register("blaze_sword",
            () -> new BlazeSwordItem(ModTiers.BLAZE, new Item.Properties()
                    .fireResistant()
                    .attributes(SwordItem.createAttributes(ModTiers.BLAZE, 3, -2.4F))));

    public static final DeferredItem<CreeperSwordItem> CREEPER_SWORD = ITEMS.register("creeper_sword",
            () -> new CreeperSwordItem(ModTiers.CREEPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.CREEPER, 3, -2.4F))));

    public static final DeferredItem<BlockingSwordItem> DARK_KATANA = ITEMS.register("dark_katana",
            () -> new BlockingSwordItem(ModTiers.DARK, new Item.Properties()
                    .rarity(Rarity.RARE)
                    .attributes(SwordItem.createAttributes(ModTiers.DARK, 8, -2.4F)),
                    true,
                    List.of(OnHit.always(MobEffects.WITHER, 120, 1))));

    public static final DeferredItem<EndermanSwordItem> ENDERMAN_SWORD = ITEMS.register("enderman_sword",
            () -> new EndermanSwordItem(ModTiers.ENDER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.ENDER, 3, -2.4F))));

    public static final DeferredItem<FireworkSwordItem> FIREWORK_SWORD = ITEMS.register("firework_sword",
            () -> new FireworkSwordItem(ModTiers.FIREWORK, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.FIREWORK, 3, -2.4F))));

    public static final DeferredItem<EffectSwordItem> GUARDIAN_SWORD = ITEMS.register("guardian_sword",
            () -> new EffectSwordItem(ModTiers.GUARDIAN, new Item.Properties()
                    .rarity(Rarity.RARE)
                    .attributes(ItemAttributes.with(
                            ItemAttributes.with(SwordItem.createAttributes(ModTiers.GUARDIAN, 3, -2.4F),
                                    Attributes.OXYGEN_BONUS, "guardian_sword_oxygen", 3.0D, EquipmentSlotGroup.HAND),
                            Attributes.WATER_MOVEMENT_EFFICIENCY, "guardian_sword_swim", 0.33D, EquipmentSlotGroup.HAND)),
                    List.of(OnHit.always(MobEffects.DIG_SLOWDOWN, 100, 1),
                            OnHit.always(MobEffects.MOVEMENT_SLOWDOWN, 60, 0))));

    public static final DeferredItem<HookSwordItem> JET = ITEMS.register("jet",
            () -> new HookSwordItem(ModTiers.JET, new Item.Properties()
                    .attributes(ItemAttributes.with(SwordItem.createAttributes(ModTiers.JET, 3, -2.2F),
                            Attributes.ENTITY_INTERACTION_RANGE, "jet_reach", 0.5D, EquipmentSlotGroup.MAINHAND))));

    public static final DeferredItem<KunaiItem> KUNAI = ITEMS.register("kunai",
            () -> new KunaiItem(new Item.Properties()
                    .stacksTo(16)
                    .attributes(ItemAttributes.melee(2.0D, -1.0D))));

    public static final DeferredItem<MonarchSwordItem> MONARCH_SWORD = ITEMS.register("monarch_sword",
            () -> new MonarchSwordItem(ModTiers.MONARCH, new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    .attributes(ItemAttributes.with(SwordItem.createAttributes(ModTiers.MONARCH, 8, -3.2F),
                            Attributes.ENTITY_INTERACTION_RANGE, "monarch_sword_reach", 1.0D, EquipmentSlotGroup.MAINHAND))));

    public static final DeferredItem<EffectSwordItem> SNAKE_SWORD = ITEMS.register("snake_sword",
            () -> new EffectSwordItem(ModTiers.SNAKE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.SNAKE, 3, -2.4F)),
                    List.of(OnHit.always(MobEffects.POISON, 100, 1),
                            new OnHit(MobEffects.CONFUSION, 80, 0, 0.25F))));

    public static final DeferredItem<WardenSwordItem> WARDEN_SWORD = ITEMS.register("warden_sword",
            () -> new WardenSwordItem(ModTiers.WARDEN, new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    .attributes(SwordItem.createAttributes(ModTiers.WARDEN, 4, -2.6F))));

    public static final DeferredItem<EffectSwordItem> WITHER_ROSE_SWORD = ITEMS.register("wither_rose_sword",
            () -> new EffectSwordItem(ModTiers.WITHER_ROSE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.WITHER_ROSE, 3, -2.4F)),
                    List.of(OnHit.always(MobEffects.WITHER, 100, 0))));

    public static final DeferredItem<InventorWrenchItem> INVENTOR_WRENCH = ITEMS.register("inventor_wrench",
            () -> new InventorWrenchItem(ModTiers.INVENTOR, new Item.Properties()
                    .rarity(Rarity.RARE)
                    .attributes(SwordItem.createAttributes(ModTiers.INVENTOR, 4, -2.8F))));

    public static final DeferredItem<WrenchItem> WRENCH = ITEMS.register("wrench",
            () -> new WrenchItem(new Item.Properties()
                    .durability(250)
                    .attributes(ItemAttributes.melee(1.0D, -2.0D))));

    public static final DeferredItem<UmbrellaItem> UMBRELLA = ITEMS.register("umbrella",
            () -> new UmbrellaItem(new Item.Properties()
                    .durability(200)
                    .attributes(ItemAttributes.melee(2.0D, -2.4D))));

    // =====================================================================
    // DIVERSOS
    // =====================================================================
    public static final DeferredItem<Item> QUARTZ_STICK = ITEMS.registerSimpleItem("quartz_stick");
    public static final DeferredItem<Item> NETHERITE_STICK = ITEMS.registerSimpleItem("netherite_stick", new Item.Properties().fireResistant());
    public static final DeferredItem<Item> OBSIDIAN_SHARD = ITEMS.registerSimpleItem("obsidian_shard");
    public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.registerSimpleItem("copper_nugget");
    public static final DeferredItem<StrawItem> STRAW = ITEMS.register("straw", () -> new StrawItem(new Item.Properties()));
    public static final DeferredItem<Item> RUBY = ITEMS.registerSimpleItem("ruby");
    public static final DeferredItem<Item> SAPHIRE = ITEMS.registerSimpleItem("saphire");
    public static final DeferredItem<Item> CHAIN_MESH = ITEMS.registerSimpleItem("chain_mesh");
    public static final DeferredItem<Item> NEZONITA_INGOT = ITEMS.registerSimpleItem("nezonita_ingot", new Item.Properties().fireResistant());
    public static final DeferredItem<Item> TEKYNITA_INGOT = ITEMS.registerSimpleItem("tekynita_ingot", new Item.Properties().fireResistant());
    public static final DeferredItem<Item> PINK_IRON_INGOT = ITEMS.registerSimpleItem("pink_iron_ingot");

    /** Pedra Filosofal: substitui o molde de melhoria de netherite para fazer Nezonita e Tekynita. */
    public static final DeferredItem<Item> PHILOSOFER_STONE = ITEMS.register("philosofer_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()) {
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }

                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                            List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag) {
                    EpicSwordItem.addAbilityTooltip(this, tooltip);
                }
            });

    // =====================================================================
    // OVOS DE SPAWN
    // =====================================================================
    public static final DeferredItem<DeferredSpawnEggItem> THE_INVENTOR_SPAWN_EGG = ITEMS.register("the_inventor_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.THE_INVENTOR, 0x5B3A1E, 0xE0A526, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> VAMPIRE_KING_SPAWN_EGG = ITEMS.register("vampire_king_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.VAMPIRE_KING, 0x111111, 0xB00010, new Item.Properties()));
    public static final DeferredItem<DeferredSpawnEggItem> KING_SLIME_SPAWN_EGG = ITEMS.register("king_slime_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.KING_SLIME, 0x7FD3C9, 0x2F4F5A, new Item.Properties()));

    // =====================================================================
    // BLOCOS
    // =====================================================================
    public static final DeferredItem<BlockItem> NEZONITA_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.NEZONITA_ORE);
    public static final DeferredItem<BlockItem> NEZONITA_CRYSTAL = ITEMS.registerSimpleBlockItem(ModBlocks.NEZONITA_CRYSTAL);
    public static final DeferredItem<BlockItem> NEZONITA_BLOCK = ITEMS.registerSimpleBlockItem("nezonita_block", ModBlocks.NEZONITA_BLOCK, new Item.Properties().fireResistant());
    public static final DeferredItem<BlockItem> TEKYNITA_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.TEKYNITA_ORE);
    public static final DeferredItem<BlockItem> TEKYNITA_CRYSTAL = ITEMS.registerSimpleBlockItem(ModBlocks.TEKYNITA_CRYSTAL);
    public static final DeferredItem<BlockItem> TEKYNITA_BLOCK = ITEMS.registerSimpleBlockItem("tekynita_block", ModBlocks.TEKYNITA_BLOCK, new Item.Properties().fireResistant());
    public static final DeferredItem<BlockItem> CHISELED_EMERALD_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_EMERALD_BLOCK);
    public static final DeferredItem<BlockItem> RUBY_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.RUBY_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_RUBY_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_RUBY_ORE);
    public static final DeferredItem<BlockItem> RUBY_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RUBY_BLOCK);
    public static final DeferredItem<BlockItem> SAPHIRE_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.SAPHIRE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SAPHIRE_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_SAPHIRE_ORE);
    public static final DeferredItem<BlockItem> SAPHIRE_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SAPHIRE_BLOCK);
    public static final DeferredItem<BlockItem> PINK_IRON_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_IRON_BLOCK);
    public static final DeferredItem<BlockItem> COPPER_CHAIN = ITEMS.registerSimpleBlockItem(ModBlocks.COPPER_CHAIN);

    // =====================================================================
    // HELPERS
    // =====================================================================
    private static Item.Properties props(boolean fireResistant) {
        Item.Properties properties = new Item.Properties();
        return fireResistant ? properties.fireResistant() : properties;
    }

    private static DeferredItem<SwordItem> sword(String name, Tier tier, int damage, float speed, boolean fireResistant) {
        return ITEMS.register(name, () -> new SwordItem(tier,
                props(fireResistant).attributes(SwordItem.createAttributes(tier, damage, speed))));
    }

    private static DeferredItem<DaggerItem> dagger(String name, Tier tier, boolean fireResistant) {
        return ITEMS.register(name, () -> new DaggerItem(tier,
                props(fireResistant).attributes(SwordItem.createAttributes(tier, DaggerItem.BASE_DAMAGE, DaggerItem.ATTACK_SPEED))));
    }

    private static DeferredItem<PickaxeItem> pickaxe(String name, Tier tier, float damage, float speed, boolean fireResistant) {
        return ITEMS.register(name, () -> new PickaxeItem(tier,
                props(fireResistant).attributes(PickaxeItem.createAttributes(tier, damage, speed))));
    }

    private static DeferredItem<AxeItem> axe(String name, Tier tier, float damage, float speed, boolean fireResistant) {
        return ITEMS.register(name, () -> new AxeItem(tier,
                props(fireResistant).attributes(AxeItem.createAttributes(tier, damage, speed))));
    }

    private static DeferredItem<ShovelItem> shovel(String name, Tier tier, float damage, float speed, boolean fireResistant) {
        return ITEMS.register(name, () -> new ShovelItem(tier,
                props(fireResistant).attributes(ShovelItem.createAttributes(tier, damage, speed))));
    }

    private static DeferredItem<HoeItem> hoe(String name, Tier tier, float damage, float speed, boolean fireResistant) {
        return ITEMS.register(name, () -> new HoeItem(tier,
                props(fireResistant).attributes(HoeItem.createAttributes(tier, damage, speed))));
    }

    private static DeferredItem<ArmorItem> armor(String name,
                                                 DeferredHolder<ArmorMaterial, ArmorMaterial> material,
                                                 ArmorItem.Type type, int durabilityMultiplier,
                                                 boolean fireResistant, String setName) {
        return ITEMS.register(name, () -> {
            Item.Properties properties = props(fireResistant).durability(type.getDurability(durabilityMultiplier));
            return setName == null
                    ? new ArmorItem(material, type, properties)
                    : new SetBonusArmorItem(material, type, properties, setName);
        });
    }

    private ModItems() {}
}
