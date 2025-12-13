package dev.tobynguyen27.astralgenerators.multiblocks.pool

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.packets.AGPackets
import dev.tobynguyen27.astralgenerators.utils.Identifier
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import kotlin.collections.set
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller

typealias Multiblocks = Object2ObjectOpenHashMap<ResourceLocation, MultiblockDefinition>

class MultiblocksPool : SimpleResourceReloadListener<Multiblocks> {

    override fun load(
        manager: ResourceManager,
        profiler: ProfilerFiller,
        executor: Executor,
    ): CompletableFuture<Multiblocks> {
        return CompletableFuture.supplyAsync(
            {
                val multiblocks = Object2ObjectOpenHashMap<ResourceLocation, MultiblockDefinition>()

                val resources = manager.listResources("multiblocks") { it.endsWith(".json") }

                for (resource in resources) {

                    if (resource.namespace != AstralGenerators.MOD_ID) continue

                    try {
                        manager.getResource(resource).inputStream.reader().use {
                            val definition = GSON.fromJson(it, MultiblockDefinition::class.java)
                            multiblocks[resource] = definition
                        }
                    } catch (e: IOException) {
                        AstralGenerators.LOGGER.error(
                            "Failed to load multiblock definition from $resource",
                            e,
                        )
                    }
                }

                return@supplyAsync multiblocks
            },
            executor,
        )
    }

    override fun apply(
        data: Multiblocks,
        manager: ResourceManager,
        profiler: ProfilerFiller,
        executor: Executor,
    ): CompletableFuture<Void> {
        return CompletableFuture.runAsync(
            {
                DEFINITIONS = data
                AstralGenerators.LOGGER.info("Loaded ${data.size} multiblocks")
            },
            executor,
        )
    }

    override fun getFabricId(): ResourceLocation {
        return ID
    }

    companion object {
        val ID = Identifier("multiblock_loader")
        val GSON: Gson = GsonBuilder().disableHtmlEscaping().create()

        var DEFINITIONS = Multiblocks()

        fun initialize() {
            ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(MultiblocksPool())

            ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(::syncDatapackToClient)
        }

        private fun syncDatapackToClient(player: ServerPlayer, joined: Boolean) {
            val packet = PacketByteBufs.create()
            packet.writeInt(DEFINITIONS.size)

            DEFINITIONS.forEach { (resourceLocation, multiblockDefinition) ->
                packet.writeResourceLocation(resourceLocation)
                packet.writeUtf(GSON.toJson(multiblockDefinition))
            }

            ServerPlayNetworking.send(player, AGPackets.MULTIBLOCK_SYNC, packet)
        }

        fun handleDatapackFromServer(minecraft: Minecraft, buf: FriendlyByteBuf) {

            val count = buf.readInt()
            val receivedData = HashMap<ResourceLocation, MultiblockDefinition>(count)

            repeat(count) {
                val id = buf.readResourceLocation()
                val json = buf.readUtf()

                receivedData[id] = GSON.fromJson(json, MultiblockDefinition::class.java)
            }

            minecraft.execute {
                DEFINITIONS.clear()
                DEFINITIONS.putAll(receivedData)
            }
        }
    }
}
