package com.roesilej.corruptedmc.corruption;

public class ClientCorruptionData {
    private static float corruptionLevel;

    public static void setCorruptionLevel(float level) {
        corruptionLevel = level;
    }

    public static float getCorruptionLevel() {
        return corruptionLevel;
    }
}