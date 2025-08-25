package com.roesilej.corruptedmc.block;

import com.roesilej.corruptedmc.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptedBlock extends Block {
    public CorruptedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // This method is called randomly on blocks that have .randomTicks() enabled.

        // Don't do anything on the client
        if (world.isClientSide()) {
            return;
        }

        // --- THIS CHECK HAS BEEN REMOVED FOR TESTING ---
        // if (random.nextInt(4) != 0) {
        //     return;
        // }

        // Check a 3x3x3 area around the block
        for (int i = 0; i < 4; i++) { // Try to spread a few times
            BlockPos nearbyPos = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
            BlockState nearbyState = world.getBlockState(nearbyPos);

            // Check if the nearby block is a valid target for corruption
            if (isValidTarget(nearbyState)) {
                world.setBlock(nearbyPos, ModBlocks.CORRUPTED_BLOCK.get().defaultBlockState(), 3);
                return; // Stop after spreading once
            }
        }
    }

    private boolean isValidTarget(BlockState state) {
        // Define which blocks can be corrupted.
        // Avoid corrupting air, bedrock, or other important blocks.
        return state.is(Blocks.STONE) || state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.ANDESITE) || state.is(Blocks.DIORITE) || state.is(Blocks.GRANITE);
    }
}