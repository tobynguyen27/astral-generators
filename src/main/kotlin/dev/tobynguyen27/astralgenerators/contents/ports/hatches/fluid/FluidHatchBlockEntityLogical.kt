package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.base.MultiblockControllerBlockEntity
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object FluidHatchBlockEntityLogical {

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: FluidHatchBlockEntity,
    ) {
        if (!blockEntity.isMatched()) return

        // Auto export
        if (
            blockEntity.autoExport == 0 &&
                blockEntity.mode == PortBlockSpecification.Mode.OUTPUT &&
                !blockEntity.fluidStorage.isResourceBlank
        ) {
            Transaction.openOuter().use {
                for (direction in Direction.entries) {
                    val neighborPos = blockPos.offset(direction.normal)

                    if (level.getBlockEntity(neighborPos) is MultiblockControllerBlockEntity)
                        continue // I'm not sure if this is useful but I still want to put it here

                    val neighborStorage =
                        FluidStorage.SIDED.find(level, neighborPos, direction) ?: continue

                    val movedAmount =
                        StorageUtil.move(
                            blockEntity.fluidStorage,
                            neighborStorage,
                            { true },
                            blockEntity.fluidStorage.amount,
                            it,
                        )

                    if (movedAmount > 0) {
                        it.commit()
                    }
                }
            }
        }

        // Auto import
        if (blockEntity.autoImport == 0 && blockEntity.mode == PortBlockSpecification.Mode.INPUT) {
            Transaction.openOuter().use {
                for (direction in Direction.entries) {
                    val neighborPos = blockPos.offset(direction.normal)

                    if (level.getBlockEntity(neighborPos) is MultiblockControllerBlockEntity)
                        continue // I'm not sure if this is useful but I still want to put it here

                    val neighborStorage =
                        FluidStorage.SIDED.find(level, neighborPos, direction) ?: continue

                    val movedAmount =
                        StorageUtil.move(
                            neighborStorage,
                            blockEntity.fluidStorage,
                            { true },
                            blockEntity.fluidStorage.capacity,
                            it,
                        )

                    if (movedAmount > 0) {
                        it.commit()
                    }
                }
            }
        }
    }
}
