package com.roesilej.corruptedmc.network.packet;

import com.roesilej.corruptedmc.corruption.ClientCorruptionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CorruptionDataSyncS2CPacket {

    private final float corruptionLevel;

    public CorruptionDataSyncS2CPacket(float corruptionLevel) {
        this.corruptionLevel = corruptionLevel;
    }

    public CorruptionDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.corruptionLevel = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.corruptionLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // We are on the client thread here
            ClientCorruptionData.setCorruptionLevel(this.corruptionLevel);
        });
        return true;
    }
}