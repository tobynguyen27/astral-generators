package dev.tobynguyen27.astralgenerators.contents.ports.buses

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.base.MultiblockControllerBlockEntity
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object BusBlockEntityLogical {

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BusBlockEntity,
    ) {
        if (!blockEntity.isMatched()) return

        // Auto export
        if (blockEntity.autoExport == 0 && blockEntity.mode == PortBlockSpecification.Mode.OUTPUT) {
            for (direction in Direction.entries) {
                val neighborPos = blockPos.offset(direction.normal)

                if (level.getBlockEntity(neighborPos) is MultiblockControllerBlockEntity)
                    continue // I'm not sure if this is useful but I still want to put it
                // here

                val neighborStorage =
                    ItemStorage.SIDED.find(level, neighborPos, direction) ?: continue

                Transaction.openOuter().use {
                    val movedAmount =
                        StorageUtil.move(blockEntity.containerWrapper, neighborStorage, { true }, 1, it)

                    if (movedAmount == 1L) {
                        it.commit()
                    }
                }
            }
        }

        // Auto import
        if (blockEntity.autoImport == 0 && blockEntity.mode == PortBlockSpecification.Mode.INPUT) {
            for (direction in Direction.entries) {
                val neighborPos = blockPos.offset(direction.normal)

                if (level.getBlockEntity(neighborPos) is MultiblockControllerBlockEntity)
                    continue // I'm not sure if this is useful but I still want to put it here

                val neighborStorage =
                    ItemStorage.SIDED.find(level, neighborPos, direction) ?: continue

                Transaction.openOuter().use {
                    val movedAmount =
                        StorageUtil.move(neighborStorage, blockEntity.containerWrapper, { true }, 1, it)

                    if (movedAmount == 1L) {
                        it.commit()
                    }
                }
            }
        }
    }
}
