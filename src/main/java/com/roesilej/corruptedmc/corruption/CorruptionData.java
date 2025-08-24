package com.roesilej.corruptedmc.corruption;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class CorruptionData extends SavedData {

    private int corruptionLevel = 0;

    public int getCorruptionLevel() {
        return corruptionLevel;
    }

    public void setCorruptionLevel(int newLevel) {
        this.corruptionLevel = Math.max(0, newLevel); // Ensure corruption doesn't go below 0
        setDirty(); // Mark this data as needing to be saved
    }

    public void increaseCorruption(int amount) {
        setCorruptionLevel(this.corruptionLevel + amount);
    }

    public static CorruptionData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access saved data from the client!");
        }

        // Get the overworld's data storage
        DimensionDataStorage storage = ((ServerLevel)level.getServer().overworld()).getDataStorage();

        // Get the data, or create it if it doesn't exist
        return storage.computeIfAbsent(CorruptionData::load, CorruptionData::new, "corruptiondata");
    }

    public static CorruptionData load(CompoundTag nbt) {
        CorruptionData data = new CorruptionData();
        data.corruptionLevel = nbt.getInt("corruption_level");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putInt("corruption_level", this.corruptionLevel);
        return nbt;
    }
}