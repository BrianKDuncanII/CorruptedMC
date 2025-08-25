package com.roesilej.corruptedmc.event;

import com.roesilej.corruptedmc.command.SetCorruptionRateCommand;
import com.roesilej.corruptedmc.command.TriggerEventCommand;
import com.roesilej.corruptedmc.config.CorruptionRateConfig;
import com.roesilej.corruptedmc.corruption.CorruptionData;
import com.roesilej.corruptedmc.init.ModBlocks;
import com.roesilej.corruptedmc.network.ModMessages;
import com.roesilej.corruptedmc.network.packet.CorruptionDataSyncS2CPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEvents {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return; // Only run logic at the end of the tick
        }

        ServerLevel overworld = event.getServer().overworld();
        CorruptionData data = CorruptionData.get(overworld);
        float corruptionLevel = data.getCorruptionLevel();

        // --- 1. Existing Logic: Increase Corruption ---
        if (overworld.getRandom().nextFloat() < CorruptionRateConfig.getEffectiveRate()) {
            data.increaseCorruption(0.01f);
            ModMessages.sendToAll(new CorruptionDataSyncS2CPacket(data.getCorruptionLevel()));
        }

        // --- 2. Existing Logic: Trigger Random Events based on Corruption ---
        // Example: At 1.0 corruption, there's a 1/5000 chance per tick to spread
        if (corruptionLevel > 1.0f && overworld.getRandom().nextFloat() < (corruptionLevel / 5000.0f)) {
            triggerCorruptionSpreadEvent(overworld, data);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Send the current corruption data to the player who just joined
            float corruptionLevel = CorruptionData.get(player.level()).getCorruptionLevel();
            ModMessages.sendToPlayer(new CorruptionDataSyncS2CPacket(corruptionLevel), player);
        }
    }

    /**
     * Increases corruption when a player kills a friendly mob (any Animal).
     */
    @SubscribeEvent
    public static void onFriendlyMobKilled(LivingDeathEvent event) {
        // Check if the killer is a player
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }

        // Check if the killed entity is a friendly mob (an Animal).
        // This includes cows, pigs, sheep, chickens, etc.
        if (event.getEntity() instanceof Animal) {
            ServerLevel world = player.serverLevel();
            CorruptionData data = CorruptionData.get(world);

            // Calculate a random increase between 0.01 and 0.03
            float increase = 0.01f + world.getRandom().nextFloat() * 0.02f; // 0.02f is the range (0.03 - 0.01)

            // Increase corruption and sync with clients
            data.increaseCorruption(increase);
            ModMessages.sendToAll(new CorruptionDataSyncS2CPacket(data.getCorruptionLevel()));
        }
    }


    /**
     * This method is called when the server is ready to register commands.
     */
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        // Register our new command class
        SetCorruptionRateCommand.register(event.getDispatcher());
        TriggerEventCommand.register(event.getDispatcher());
    }

    private static void triggerCorruptionSpreadEvent(ServerLevel world, CorruptionData data) {
        // Pick a random player
        if (world.getServer().getPlayerCount() == 0) return;
        ServerPlayer player = world.getServer().getPlayerList().getPlayers().get(world.getRandom().nextInt(world.getServer().getPlayerCount()));

        // Try to find a suitable block to corrupt around the player
        for (int i = 0; i < 10; i++) { // Try 10 times to find a spot
            BlockPos targetPos = player.blockPosition().offset(
                    world.getRandom().nextInt(16) - 8,
                    world.getRandom().nextInt(8) - 4,
                    world.getRandom().nextInt(16) - 8
            );

            BlockState targetState = world.getBlockState(targetPos);
            // We can reuse the same validation logic from the CorruptedBlock class
            if (targetState.is(Blocks.STONE) || targetState.is(Blocks.DIRT) || targetState.is(Blocks.GRASS_BLOCK)) {
                world.setBlock(targetPos, ModBlocks.CORRUPTED_BLOCK.get().defaultBlockState(), 3);
                return; // Exit after corrupting one block
            }
        }
    }
}