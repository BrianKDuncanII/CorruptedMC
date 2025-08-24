package com.roesilej.corruptedmc.client;

import com.roesilej.corruptedmc.CorruptedMC;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CorruptedMC.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("corruption", CorruptionHudOverlay.HUD_CORRUPTION);
    }

    @Mod.EventBusSubscriber(modid = CorruptedMC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onScreenRender(ScreenEvent.Render.Post event) {
            if (event.getScreen() instanceof TitleScreen) {
                String version = ModList.get().getModContainerById(CorruptedMC.MODID).get().getModInfo().getVersion().toString();
                String text = "Corrupted MC (" + version + ") by Roesilej";
                int screenWidth = event.getScreen().width;
                int y = event.getScreen().height - 22; // 22 pixels from the bottom
                // Corrected line
                int stringWidth = event.getScreen().getMinecraft().font.width(text);

                event.getGuiGraphics().drawString(
                    event.getScreen().getMinecraft().font,
                    text,
                    screenWidth - stringWidth - 2, // 2 pixels from the right edge
                    y,
                    0xFFFFFF, // White color
                    true // Render with shadow
                );
            }
        }
    }
}