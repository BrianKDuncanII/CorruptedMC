package com.roesilej.corruptedmc.client;

import com.roesilej.corruptedmc.CorruptedMC;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

// This annotation tells Forge to listen for events in this class
@Mod.EventBusSubscriber(modid = CorruptedMC.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    // This is the new, correct way to register a HUD overlay
    @SubscribeEvent
    public static void registerGuiOverlays(final RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("corruption_hud", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            // We call the render method from our existing overlay class
            CorruptionHudOverlay.render(guiGraphics, screenWidth, screenHeight);
        });
    }

    // --- We can keep your title screen renderer here too ---
    // This part of your code was fine, it just needed to be registered.
    // By placing it in this annotated class, it will be automatically registered by Forge.
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onScreenRender(ScreenEvent.Render.Post event) {
            if (event.getScreen() instanceof TitleScreen) {
                // Your existing code for rendering the version on the title screen
                String text = "Corrupted MC v0.1.0"; // Simplified for this example
                int screenWidth = event.getScreen().width;
                int y = event.getScreen().height - 22;
                int stringWidth = event.getScreen().getMinecraft().font.width(text);

                event.getGuiGraphics().drawString(
                    event.getScreen().getMinecraft().font,
                    text,
                    screenWidth - stringWidth - 2,
                    y,
                    0xFFFFFF,
                    true
                );
            }
        }
    }
}