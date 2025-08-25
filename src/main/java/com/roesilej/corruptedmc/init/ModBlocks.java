package com.roesilej.corruptedmc.init;

import com.roesilej.corruptedmc.CorruptedMC;
import com.roesilej.corruptedmc.block.CorruptedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks; // <-- Add this import
import net.minecraft.world.level.block.state.BlockBehaviour;
// import net.minecraft.world.level.material.Material; // <-- REMOVE this import
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CorruptedMC.MODID);

    // Register the new Corrupted Block
    public static final RegistryObject<Block> CORRUPTED_BLOCK = registerBlock("corrupted_block",
            // V-- THIS IS THE CORRECTED LINE --V
            () -> new CorruptedBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .randomTicks())); // This is crucial for spreading!

    // Helper method for registering blocks
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
}