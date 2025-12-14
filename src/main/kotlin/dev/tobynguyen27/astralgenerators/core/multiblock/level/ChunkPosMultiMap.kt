package dev.tobynguyen27.astralgenerators.core.multiblock.level

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

class ChunkPosMultiMap<T> {
    val storage =
        Object2ObjectOpenHashMap<Level, Object2ObjectOpenHashMap<ChunkPos, ObjectOpenHashSet<T>>>()

    fun get(level: Level, chunkPos: ChunkPos): ObjectOpenHashSet<T>? {
        val chunkPosSetMap = storage.get(level) ?: return null

        return chunkPosSetMap.get(chunkPos)
    }

    fun size(): Int {
        return storage.size
    }

    fun remove(level: Level, chunkPos: ChunkPos, t: T) {
        val chunkPosMap = storage[level]
        val tSet = chunkPosMap?.get(chunkPos)

        if (tSet == null || !tSet.remove(t)) {
            throw RuntimeException(
                "Could not remove element at position $chunkPos as it does not exist."
            )
        }

        if (tSet.isEmpty()) {
            chunkPosMap.remove(chunkPos)
            if (chunkPosMap.isEmpty()) {
                storage.remove(level)
            }
        }
    }

    fun add(level: Level, chunkPos: ChunkPos, t: T) {
        storage
            .getOrPut(level) { Object2ObjectOpenHashMap() }
            .getOrPut(chunkPos) { ObjectOpenHashSet() }
            .add(t)
    }
}
