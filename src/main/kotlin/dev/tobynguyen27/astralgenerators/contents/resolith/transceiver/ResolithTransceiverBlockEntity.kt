package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ResolithTransceiverBlockEntity(
    type: BlockEntityType<out ResolithTransceiverBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithBlockEntity(type, blockPos, blockState) {}
