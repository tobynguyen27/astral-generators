package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchMenu
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class IndustrialFluidInputHatchBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : FluidHatchBlockEntity(blockEntityType, blockPos, blockState, CAPACITY, TIER, MODE, null) {

    companion object {
        private val CAPACITY = 128
        private val TIER = PortBlockSpecification.Tier.INDUSTRIAL
        val MODE = PortBlockSpecification.Mode.INPUT
    }

    override val menuFactory: (Int, Inventory, ContainerLevelAccess) -> AbstractContainerMenu =
        { syncId, inventory, ctx ->
            FluidHatchMenu(MODE, AGMenus.INDUSTRIAL_FLUID_INPUT_HATCH, syncId, inventory, ctx)
        }

    override fun getPortType(): PortBlockType {
        return PortBlockType.FLUID_INPUT
    }
}
