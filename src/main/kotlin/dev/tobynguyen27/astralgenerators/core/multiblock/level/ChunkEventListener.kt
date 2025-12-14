package dev.tobynguyen27.astralgenerators.core.multiblock.level

import net.minecraft.core.BlockPos

interface ChunkEventListener {
    fun onBlockUpdate(pos: BlockPos)

    fun onUnload()

    fun onLoad()
}
