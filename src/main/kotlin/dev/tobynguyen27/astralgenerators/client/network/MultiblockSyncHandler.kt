package dev.tobynguyen27.astralgenerators.client.network

import dev.tobynguyen27.astralgenerators.multiblocks.pool.MultiblocksPool
import dev.tobynguyen27.astralgenerators.packets.AGPackets
import kotlin.collections.set
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object MultiblockSyncHandler {

    fun register() {
        ClientPlayNetworking.registerGlobalReceiver(AGPackets.MULTIBLOCK_SYNC) {
            minecraft,
            _,
            buf,
            _ ->
            MultiblocksPool.handleDatapackFromServer(minecraft, buf)
        }

        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            MultiblocksPool.DEFINITIONS.clear()
        }
    }
}
