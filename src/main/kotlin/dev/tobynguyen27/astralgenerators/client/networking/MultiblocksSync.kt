package dev.tobynguyen27.astralgenerators.client.networking

import com.google.gson.Gson
import dev.tobynguyen27.astralgenerators.multiblocks.pool.MultiblockDefinition
import dev.tobynguyen27.astralgenerators.multiblocks.pool.MultiblocksPool
import dev.tobynguyen27.astralgenerators.packets.S2CPackets
import kotlin.collections.set
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.resources.ResourceLocation

object MultiblocksSync {

    fun register() {
        ClientPlayNetworking.registerGlobalReceiver(S2CPackets.MULTIBLOCK_SYNC) {
            minecraft,
            _,
            packet,
            _ ->
            val count = packet.readInt()
            val multiBlockMap = hashMapOf<ResourceLocation, MultiblockDefinition>()

            repeat(count) {
                val id = packet.readResourceLocation()
                val json = packet.readUtf()
                multiBlockMap[id] = Gson().fromJson(json, MultiblockDefinition::class.java)
            }

            minecraft.execute {
                multiBlockMap.forEach { (key, value) -> MultiblocksPool.DEFINITIONS[key] = value }
            }
        }

        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            MultiblocksPool.DEFINITIONS.clear()
        }
    }
}
