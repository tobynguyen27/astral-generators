package dev.tobynguyen27.astralgenerators.client.network

import dev.tobynguyen27.astralgenerators.core.multiblock.pool.MultiblocksPool
import dev.tobynguyen27.astralgenerators.core.network.Packets
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object MultiblockSyncHandler {

    fun register() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.MULTIBLOCK_SYNC) { minecraft, _, buf, _
            ->
            MultiblocksPool.handleDatapackFromServer(minecraft, buf)
        }

        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            MultiblocksPool.DEFINITIONS = emptyMap()
        }
    }
}
