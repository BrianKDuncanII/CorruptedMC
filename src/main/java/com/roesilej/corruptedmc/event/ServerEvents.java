package com.roesilej.corruptedmc.event;

import com.roesilej.corruptedmc.CorruptedMC;
import com.roesilej.corruptedmc.corruption.CorruptionData;
import com.roesilej.corruptedmc.network.ModMessages;
import com.roesilej.corruptedmc.network.packet.CorruptionDataSyncS2CPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CorruptedMC.MODID)
public class ServerEvents {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // We'll use a random chance to increase corruption to make it feel more natural.
            // This check happens every tick, so the probability should be low.
            ServerLevel overworld = event.getServer().overworld();
            if (overworld.getRandom().nextFloat() < 0.0005f) { // A small chance to trigger
                CorruptionData data = CorruptionData.get(overworld);
                data.increaseCorruption(0.01f);

                // Send update to all players
                ModMessages.sendToAll(new CorruptionDataSyncS2CPacket(data.getCorruptionLevel()));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Send the current corruption data to the player who just joined
            float corruptionLevel = CorruptionData.get(player.level()).getCorruptionLevel();
            ModMessages.sendToPlayer(new CorruptionDataSyncS2CPacket(corruptionLevel), player);
        }
    }
}