package com.nekyoh.epiccraft.common.event;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.enchantment.ModEnchantments;
import com.nekyoh.epiccraft.common.item.ModItems;
import com.nekyoh.epiccraft.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.UUID;

/** Eventos gerais: Piglin Brute com machado, troca do vendedor ambulante e tags de dev. */
@EventBusSubscriber(modid = EpicCraft.MODID)
public final class WorldEvents {
    private static final String BRUTE_TAG = EpicCraft.MODID + ":brute_checked";

    private static final UUID TEKYOH_UUID = UUID.fromString("e366ea04-ee06-4a4c-93b1-b1e74859c746");
    private static final UUID NEZORU_UUID = UUID.fromString("5a595db8-d17f-4318-a6b6-54ac5cd79b93");

    // ---------------------------------------------------------------------
    // Piglin Brute nasce com o Brute Battle Axe (8% de drop).
    // Usa uma marca no NBT para só trocar uma vez (antes trocava toda vez que o chunk carregava).
    // ---------------------------------------------------------------------
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof PiglinBrute brute)) return;
        if (brute.getPersistentData().getBoolean(BRUTE_TAG)) return;
        brute.getPersistentData().putBoolean(BRUTE_TAG, true);

        // Só substitui o machado de ouro padrão (não mexe em brutes com equipamento customizado)
        ItemStack hand = brute.getItemInHand(InteractionHand.MAIN_HAND);
        if (hand.isEmpty() || hand.is(Items.GOLDEN_AXE)) {
            brute.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.BRUTE_BATTLE_AXE.get()));
            brute.setDropChance(EquipmentSlot.MAINHAND, 0.08F);
        }
    }

    // ---------------------------------------------------------------------
    // Vendedor ambulante vende o livro Emerald Grace I (troca rara)
    // ---------------------------------------------------------------------
    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event) {
        event.getRareTrades().add((trader, random) ->
                ModEnchantments.get(trader.level().registryAccess(), ModEnchantments.EMERALD_GRACE)
                        .map(holder -> new MerchantOffer(
                                new ItemCost(Items.EMERALD, 32),
                                EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holder, 1)),
                                2, 10, 0.05F))
                        .orElse(null));
    }

    // ---------------------------------------------------------------------
    // Tags de Dev/Artista no TAB (configurável)
    // ---------------------------------------------------------------------
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!Config.SHOW_DEV_TAGS.get()) return;

        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        Scoreboard scoreboard = player.level().getScoreboard();
        UUID uuid = player.getUUID();
        if (uuid.equals(TEKYOH_UUID)) {
            setupTeam(scoreboard, player.getScoreboardName(), "ec_dev", "Dev(Epic craft)", ChatFormatting.BLUE);
        } else if (uuid.equals(NEZORU_UUID)) {
            setupTeam(scoreboard, player.getScoreboardName(), "ec_artist", "Artist(Epic craft)", ChatFormatting.RED);
        }
    }

    private static void setupTeam(Scoreboard scoreboard, String playerName, String teamId, String role, ChatFormatting color) {
        PlayerTeam team = scoreboard.getPlayerTeam(teamId);
        if (team == null) {
            team = scoreboard.addPlayerTeam(teamId);
        }
        team.setColor(color);
        team.setPlayerSuffix(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(role).withStyle(ChatFormatting.AQUA)));
        team.setCollisionRule(Team.CollisionRule.ALWAYS);
        scoreboard.addPlayerToTeam(playerName, team);
    }

    private WorldEvents() {}
}
