package com.roesilej.corruptedmc;

import com.mojang.logging.LogUtils;
import com.roesilej.corruptedmc.network.ModMessages;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CorruptedMC.MODID)
public class CorruptedMC {
    public static final String MODID = "corruptedmc";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CorruptedMC() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // You can register other things here later, like items, blocks, etc.
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // We must do this during the setup event.
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }
}