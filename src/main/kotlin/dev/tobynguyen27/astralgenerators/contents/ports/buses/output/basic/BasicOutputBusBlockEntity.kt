package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic

import dev.tobynguyen27.astralgenerators.contents.ports.BusBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BasicOutputBusBlockEntity(
    type: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BusBlockEntity(type, blockPos, blockState, CONTAINER_SIZE, Tier.BASIC, Mode.OUTPUT, null) {

    companion object {
        const val CONTAINER_SIZE = 1
    }

    override fun createMenu(
        syncId: Int,
        inventory: Inventory,
        player: Player,
    ): AbstractContainerMenu {
        return BasicOutputBusMenu(
            syncId,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }
}
