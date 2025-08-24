package com.roesilej.corruptedmc.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CorruptionHudOverlay {
    // For now, we are just displaying text. You could use a texture for a corruption symbol later.
    // private static final ResourceLocation CORRUPTION_ICON = new ResourceLocation("corruptedmc", "textures/gui/corruption.png");

    public static final IGuiOverlay HUD_CORRUPTION = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        // For this example, we'll just use a placeholder for the corruption level on the client.
        // In a more advanced mod, you would sync this value from the server to the client.
        int corruptionLevel = 0; // Placeholder value
        String text = "Corruption: " + corruptionLevel + "%";

        int x = 10;
        int y = 10;

        // Don't render if the debug screen (F3) is open
        if (!Minecraft.getInstance().options.renderDebug) {
            guiGraphics.drawString(Minecraft.getInstance().font, text, x, y, 0xFFFFFF);
        }
    };
}