package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BasicFluidOutputHatchBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : FluidHatchBlockEntity(blockEntityType, blockPos, blockState, CAPACITY, TIER, MODE, null) {

    companion object {
        private val CAPACITY = 8
        private val TIER = PortBlockSpecification.Tier.BASIC
        private val MODE = PortBlockSpecification.Mode.OUTPUT
    }

    override fun createMenu(
        syncId: Int,
        inventory: Inventory,
        player: Player,
    ): AbstractContainerMenu {
        return BasicFluidOutputHatchMenu(
            syncId,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }
}
