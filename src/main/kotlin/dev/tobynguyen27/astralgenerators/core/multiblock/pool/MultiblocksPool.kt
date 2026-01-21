package dev.tobynguyen27.astralgenerators.core.multiblock.pool

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.core.util.Identifier
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

typealias Multiblocks = Map<ResourceLocation, MultiblockDefinition>

class MultiblocksPool : SimpleResourceReloadListener<Multiblocks> {

    override fun load(
        manager: ResourceManager,
        profiler: ProfilerFiller,
        executor: Executor,
    ): CompletableFuture<Multiblocks> {
        return CompletableFuture.supplyAsync(
            {
                val multiblocks =
                    buildMap {
                            manager
                                .listResources("multiblocks") { it.endsWith(".json") }
                                .filter { it.namespace == AstralGenerators.MOD_ID }
                                .forEach { resource ->
                                    runCatching {
                                            manager.getResource(resource).inputStream.reader().use {
                                                GSON.fromJson(it, MultiblockDefinition::class.java)
                                            }
                                        }
                                        .onSuccess { put(resource, it) }
                                        .onFailure {
                                            AstralGenerators.LOGGER.error(
                                                "Failed to load multiblock definition from $resource",
                                                it,
                                            )
                                        }
                                }
                        }
                        .toMap()

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

    override fun getFabricId(): ResourceLocation = Identifier("multiblock_loader")

    companion object {
        val GSON: Gson = GsonBuilder().disableHtmlEscaping().create()

        var DEFINITIONS: Multiblocks = emptyMap()

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

            ServerPlayNetworking.send(player, Packets.MULTIBLOCK_SYNC, packet)
        }

        fun handleDatapackFromServer(minecraft: Minecraft, buf: FriendlyByteBuf) {

            val count = buf.readInt()
            val receivedData = HashMap<ResourceLocation, MultiblockDefinition>(count)

            repeat(count) {
                val id = buf.readResourceLocation()
                val json = buf.readUtf()

                receivedData[id] = GSON.fromJson(json, MultiblockDefinition::class.java)
            }

            minecraft.execute { DEFINITIONS = receivedData }
        }
    }
}
