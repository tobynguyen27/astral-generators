package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AdvancedInputBusBlockEntity(
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
        PortBlockSpecification.Mode.INPUT,
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
        return AdvancedInputBusMenu(
            syncId,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }

    override fun getPortType(): PortBlockType {
        return PortBlockType.ITEM_INPUT
    }
}
