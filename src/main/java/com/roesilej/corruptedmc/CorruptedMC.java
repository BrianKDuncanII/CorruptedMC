package com.roesilej.corruptedmc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CorruptedMC.MODID)
public class CorruptedMC {
    public static final String MODID = "corruptedmc";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CorruptedMC() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // You can register other things here later, like items, blocks, etc.
    }
}