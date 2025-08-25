package com.roesilej.corruptedmc.init;

import com.roesilej.corruptedmc.CorruptedMC;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CorruptedMC.MODID);

    // Register the BlockItem for our Corrupted Block
    public static final RegistryObject<Item> CORRUPTED_BLOCK_ITEM = ITEMS.register("corrupted_block",
            () -> new BlockItem(ModBlocks.CORRUPTED_BLOCK.get(), new Item.Properties()));
}