package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchMenu
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AdvancedFluidOutputHatchBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : FluidHatchBlockEntity(blockEntityType, blockPos, blockState, CAPACITY, TIER, MODE, null) {

    companion object {
        private val CAPACITY = 32
        private val TIER = PortBlockSpecification.Tier.ADVANCED
        val MODE = PortBlockSpecification.Mode.OUTPUT
    }

    override fun createMenu(
        syncId: Int,
        inventory: Inventory,
        player: Player,
    ): AbstractContainerMenu {
        return FluidHatchMenu(
            MODE,
            AGMenus.ADVANCED_FLUID_OUTPUT_HATCH,
            syncId,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }

    override fun getPortType(): PortBlockType {
        return PortBlockType.FLUID_OUTPUT
    }
}
