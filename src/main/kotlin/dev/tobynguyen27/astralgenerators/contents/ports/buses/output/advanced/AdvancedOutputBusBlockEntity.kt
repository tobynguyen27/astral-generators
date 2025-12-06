package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AdvancedOutputBusBlockEntity(
    type: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) :
    BusBlockEntity(
        type,
        blockPos,
        blockState,
        CONTAINER_SIZE,
        PortBlockSpecification.Tier.ADVANCED,
        PortBlockSpecification.Mode.OUTPUT,
        null,
    ) {

    companion object {
        const val CONTAINER_SIZE = 9
    }

    override fun createMenu(
        syncId: Int,
        inventory: Inventory,
        player: Player,
    ): AbstractContainerMenu {
        return AdvancedOutputBusMenu(
            syncId,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }
}
