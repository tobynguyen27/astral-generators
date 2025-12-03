package dev.tobynguyen27.astralgenerators.contents.ports

import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

abstract class BusBlock(properties: Properties) : BaseEntityBlock(properties) {

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }
}
