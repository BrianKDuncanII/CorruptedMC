package com.roesilej.corruptedmc.client;

import com.roesilej.corruptedmc.corruption.ClientCorruptionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CorruptionHudOverlay {
    // For now, we are just displaying text. You could use a texture for a corruption symbol later.
    // private static final ResourceLocation CORRUPTION_ICON = new ResourceLocation("corruptedmc", "textures/gui/corruption.png");

    public static final IGuiOverlay HUD_CORRUPTION = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = 10; // Position from the top of the screen

        float corruptionLevel = ClientCorruptionData.getCorruptionLevel();
        String text = "Corruption: " + String.format("%.2f", corruptionLevel);

        // Don't render if the debug screen (F3) is open
        if (!Minecraft.getInstance().options.renderDebug) {
            int stringWidth = Minecraft.getInstance().font.width(text);
            int centeredX = x - (stringWidth / 2);
            guiGraphics.drawString(Minecraft.getInstance().font, text, centeredX, y, 0xFFFFFF, true);
        }
    };
}