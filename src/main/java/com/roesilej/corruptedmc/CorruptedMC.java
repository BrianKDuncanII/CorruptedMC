package com.roesilej.corruptedmc;

import com.mojang.logging.LogUtils;
import com.roesilej.corruptedmc.client.ClientEvents;
import com.roesilej.corruptedmc.client.CorruptionHudOverlay;
import com.roesilej.corruptedmc.event.ServerEvents;
import com.roesilej.corruptedmc.init.ModBlocks;
import com.roesilej.corruptedmc.init.ModItems;
import com.roesilej.corruptedmc.network.ModMessages;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CorruptedMC.MODID)
public class CorruptedMC {
    public static final String MODID = "corruptedmc";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CorruptedMC() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register server-side events like commands and server ticks
        MinecraftForge.EVENT_BUS.register(ServerEvents.class);
        MinecraftForge.EVENT_BUS.register(ClientEvents.ForgeEvents.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }
}