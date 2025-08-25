package com.roesilej.corruptedmc.config;

public class CorruptionRateConfig {
    // This is the default probability of corruption increasing each server tick.
    public static final float DEFAULT_RATE = 0.0005f;

    // This multiplier will be adjusted by our command. It starts at 1 for the default rate.
    private static float rateMultiplier = 1.0f;

    /**
     * Sets the multiplier for the corruption rate.
     * @param multiplier The new multiplier. Value is clamped to be non-negative.
     */
    public static void setRateMultiplier(float multiplier) {
        // We use Math.max to prevent anyone from setting a negative multiplier.
        CorruptionRateConfig.rateMultiplier = Math.max(0, multiplier);
    }

    /**
     * Gets the current corruption rate multiplier.
     * @return The current multiplier.
     */
    public static float getRateMultiplier() {
        return rateMultiplier;
    }

    /**
     * Calculates the effective rate by combining the default rate and the multiplier.
     * @return The final probability for the corruption tick.
     */
    public static float getEffectiveRate() {
        return DEFAULT_RATE * rateMultiplier;
    }
}