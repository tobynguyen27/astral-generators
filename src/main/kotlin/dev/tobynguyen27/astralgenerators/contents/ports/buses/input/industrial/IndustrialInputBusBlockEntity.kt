package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class IndustrialInputBusBlockEntity(
    type: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) :
    BusBlockEntity(
        type,
        blockPos,
        blockState,
        CONTAINER_SIZE,
        PortBlockSpecification.Tier.INDUSTRIAL,
        PortBlockSpecification.Mode.INPUT,
        null,
    ) {

    companion object {
        const val CONTAINER_SIZE = 25
    }

    override val menuFactory: (Int, Inventory, ContainerLevelAccess) -> AbstractContainerMenu =
        ::IndustrialInputBusMenu

    override fun getPortType(): PortBlockType {
        return PortBlockType.ITEM_INPUT
    }
}
