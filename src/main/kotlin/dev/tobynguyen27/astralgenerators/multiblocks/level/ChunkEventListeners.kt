package dev.tobynguyen27.astralgenerators.multiblocks.level

import dev.tobynguyen27.codebebelib.utils.ServerUtils
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStopped
import net.minecraft.core.BlockPos
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

object ChunkEventListeners {
    var listeners = ChunkPosMultiMap<ChunkEventListener>()

    fun initialize() {
        ServerLifecycleEvents.SERVER_STOPPED.register(ServerStopped { _ -> serverStopCleanup() })

        ServerChunkEvents.CHUNK_LOAD.register(
            ServerChunkEvents.Load { level, chunk ->
                ensureServerThread()
                val cels = listeners.get(level, chunk.pos)
                if (cels != null) {
                    for (cel in cels) {
                        cel.onLoad()
                    }
                }
            }
        )
        ServerChunkEvents.CHUNK_UNLOAD.register(
            ServerChunkEvents.Unload { level, chunk ->
                ensureServerThread()
                val cels = listeners.get(level, chunk.pos)
                if (cels != null) {
                    for (cel in cels) {
                        cel.onUnload()
                    }
                }
            }
        )
    }

    @JvmStatic
    fun onBlockStateChange(level: Level, chunkPos: ChunkPos, pos: BlockPos) {
        if (ServerUtils.getServer().isSameThread) {
            val cels: MutableSet<ChunkEventListener>? = listeners.get(level, chunkPos)
            if (cels != null) {
                for (cel in cels) {
                    cel.onBlockUpdate(pos)
                }
            }
        }
    }

    private fun ensureServerThread() {
        if (!ServerUtils.getServer().isSameThread) {
            throw RuntimeException("Thread is not server thread!")
        }
    }

    private fun serverStopCleanup() {
        if (listeners.size() != 0) {
            listeners = ChunkPosMultiMap()
        }
    }
}
