package com.roesilej.corruptedmc.event;

import com.roesilej.corruptedmc.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptionEvents {

    /**
     * Attempts to place a single Corrupted Block near a random player in the world.
     * This is the "block spread" event.
     * @param world The server world to perform the event in.
     * @return True if the event succeeded, false otherwise.
     */
    public static boolean triggerCorruptionSpreadEvent(ServerLevel world) {
        // Pick a random player
        if (world.getServer().getPlayerCount() == 0) return false;
        ServerPlayer player = world.getServer().getPlayerList().getPlayers().get(world.getRandom().nextInt(world.getServer().getPlayerCount()));

        // Try to find a suitable block to corrupt around the player
        for (int i = 0; i < 15; i++) { // Try 15 times to find a spot
            BlockPos targetPos = player.blockPosition().offset(
                    world.getRandom().nextInt(20) - 10, // Increased range slightly
                    world.getRandom().nextInt(10) - 5,
                    world.getRandom().nextInt(20) - 10
            );
            BlockState targetState = world.getBlockState(targetPos);
            // We can reuse the same validation logic from the CorruptedBlock class
            if (targetState.is(Blocks.STONE) || targetState.is(Blocks.DIRT) || targetState.is(Blocks.GRASS_BLOCK)) {
                world.setBlock(targetPos, ModBlocks.CORRUPTED_BLOCK.get().defaultBlockState(), 3);
                // Optional: Notify the server console that the event was triggered
                // CorruptedMC.LOGGER.info("Triggered 'spread' event at: " + targetPos);
                return true; // The event was successful
            }
        }
        return false; // Failed to find a suitable location
    }

    /**
     * Triggers a small, non-destructive explosion near a random player.
     * @param world The server world to perform the event in.
     * @return True if the event succeeded, false otherwise.
     */
    public static boolean triggerExplosionEvent(ServerLevel world) {
        // Pick a random player
        if (world.getServer().getPlayerCount() == 0) return false;
        ServerPlayer player = world.getServer().getPlayerList().getPlayers().get(world.getRandom().nextInt(world.getServer().getPlayerCount()));

        // Find a random position near the player for the explosion
        BlockPos explosionPos = player.blockPosition().offset(
                world.getRandom().nextInt(16) - 8, // x-offset between -8 and 7
                0,                                 // y-offset at player's feet level to avoid underground/sky explosions
                world.getRandom().nextInt(16) - 8  // z-offset between -8 and 7
        );

        // Create an explosion that doesn't break blocks but still damages entities
        // To make it destroy blocks, change Level.ExplosionInteraction.NONE to Level.ExplosionInteraction.BLOCK
        world.explode(null, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), 2.0f, Level.ExplosionInteraction.NONE);

        // This event is considered successful once the explosion is triggered
        return true;
    }

    // You can add more public static methods here for future events!
    // public static boolean triggerAnotherEvent(ServerLevel world) { ... }
}