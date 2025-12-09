package dev.tobynguyen27.astralgenerators.multiblocks.level

import net.minecraft.core.BlockPos

interface ChunkEventListener {
    fun onBlockUpdate(pos: BlockPos)

    fun onUnload()

    fun onLoad()
}
