package com.roesilej.corruptedmc.client;

import com.roesilej.corruptedmc.corruption.ClientCorruptionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class CorruptionHudOverlay {
    public static void render(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        if (!Minecraft.getInstance().options.renderDebug) {
            int y = 10;
            float corruptionLevel = ClientCorruptionData.getCorruptionLevel();
            String text = "Corruption: " + String.format("%.2f%%", corruptionLevel);

            int stringWidth = Minecraft.getInstance().font.width(text);
            int centeredX = (screenWidth - stringWidth) / 2;

            guiGraphics.drawString(Minecraft.getInstance().font, text, centeredX, y, 0xFFFFFF, true);
        }
    }
}