package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchMenu
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class IndustrialEnergyOutputHatchBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : EnergyHatchBlockEntity(blockEntityType, blockPos, blockState, CAPACITY, TIER, MODE, null) {

    companion object {
        private val CAPACITY = 256000L
        private val TIER = PortBlockSpecification.Tier.INDUSTRIAL
        val MODE = PortBlockSpecification.Mode.OUTPUT
    }

    override val menuFactory: (Int, Inventory, ContainerLevelAccess) -> AbstractContainerMenu =
        { syncId, inventory, ctx ->
            EnergyHatchMenu(MODE, AGMenus.INDUSTRIAL_ENERGY_OUTPUT_HATCH, syncId, inventory, ctx)
        }

    override fun getPortType(): PortBlockType {
        return PortBlockType.ENERGY_OUTPUT
    }
}
