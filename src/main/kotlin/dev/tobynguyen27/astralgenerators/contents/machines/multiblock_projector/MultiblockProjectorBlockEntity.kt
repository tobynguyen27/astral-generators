package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class MultiblockProjectorBlockEntity(
    type: BlockEntityType<MultiblockProjectorBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BlockEntity(type, blockPos, blockState) {}
