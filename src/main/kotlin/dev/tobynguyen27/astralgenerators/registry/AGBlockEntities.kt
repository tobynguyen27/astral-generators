package dev.tobynguyen27.astralgenerators.registry

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector.MultiblockProjectorBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced.AdvancedEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic.BasicEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.industrial.IndustrialEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced.AdvancedEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic.BasicEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial.IndustrialEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import net.minecraft.core.Registry

object AGBlockEntities {

    val RAW_RESOLITH_PYLON: BlockEntityEntry<ResolithBlockEntity> =
        BlockEntityEntry.cast(AGBlocks.RAW_RESOLITH_PYLON.getSibling(Registry.BLOCK_ENTITY_TYPE))

    val PURE_RESOLITH_PYLON: BlockEntityEntry<ResolithBlockEntity> =
        BlockEntityEntry.cast(AGBlocks.PURE_RESOLITH_PYLON.getSibling(Registry.BLOCK_ENTITY_TYPE))

    val RAW_RESOLITH_TRANSCEIVER: BlockEntityEntry<ResolithBlockEntity> =
        BlockEntityEntry.cast(
            AGBlocks.RAW_RESOLITH_TRANSCEIVER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val PURE_RESOLITH_TRANSCEIVER: BlockEntityEntry<ResolithBlockEntity> =
        BlockEntityEntry.cast(
            AGBlocks.PURE_RESOLITH_TRANSCEIVER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val ASSEMBLER: BlockEntityEntry<AssemblerBlockEntity> =
        BlockEntityEntry.cast(AGBlocks.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE))

    val AM_CONTROLLER: BlockEntityEntry<AMControllerBlockEntity> =
        BlockEntityEntry.cast(AGBlocks.AM_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE))

    val BOILER_CONTROLLER: BlockEntityEntry<BoilerControllerBlockEntity> =
        BlockEntityEntry.cast(AGBlocks.BOILER_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE))

    val STEAM_TURBINE_CONTROLLER: BlockEntityEntry<SteamTurbineControllerBlockEntity> =
        BlockEntityEntry.cast(
            AGBlocks.STEAM_TURBINE_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val MULTIBLOCK_PROJECTOR: BlockEntityEntry<MultiblockProjectorBlockEntity> =
        BlockEntityEntry.cast(AGBlocks.MULTIBLOCK_PROJECTOR.getSibling(Registry.BLOCK_ENTITY_TYPE))

    // Buses
    val BASIC_INPUT_BUS: BlockEntityEntry<BasicInputBusBlockEntity> =
        BlockEntityEntry.cast(AGPortBlocks.BASIC_INPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE))
    val ADVANCED_INPUT_BUS: BlockEntityEntry<AdvancedInputBusBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.ADVANCED_INPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_INPUT_BUS: BlockEntityEntry<IndustrialInputBusBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.INDUSTRIAL_INPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val BASIC_OUTPUT_BUS: BlockEntityEntry<BasicOutputBusBlockEntity> =
        BlockEntityEntry.cast(AGPortBlocks.BASIC_OUTPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE))
    val ADVANCED_OUTPUT_BUS: BlockEntityEntry<AdvancedOutputBusBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.ADVANCED_OUTPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_OUTPUT_BUS: BlockEntityEntry<IndustrialOutputBusBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.INDUSTRIAL_OUTPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    // Fluid hatches
    val BASIC_FLUID_INPUT_HATCH: BlockEntityEntry<BasicFluidInputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.BASIC_FLUID_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_FLUID_INPUT_HATCH: BlockEntityEntry<AdvancedFluidInputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.ADVANCED_FLUID_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_FLUID_INPUT_HATCH: BlockEntityEntry<IndustrialFluidInputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.INDUSTRIAL_FLUID_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val BASIC_FLUID_OUTPUT_HATCH: BlockEntityEntry<BasicFluidOutputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.BASIC_FLUID_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_FLUID_OUTPUT_HATCH: BlockEntityEntry<AdvancedFluidOutputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.ADVANCED_FLUID_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_FLUID_OUTPUT_HATCH: BlockEntityEntry<IndustrialFluidOutputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.INDUSTRIAL_FLUID_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    // Energy hatches
    val BASIC_ENERGY_INPUT_HATCH: BlockEntityEntry<BasicEnergyInputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.BASIC_ENERGY_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_ENERGY_INPUT_HATCH: BlockEntityEntry<AdvancedEnergyInputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.ADVANCED_ENERGY_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_ENERGY_INPUT_HATCH: BlockEntityEntry<IndustrialEnergyInputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.INDUSTRIAL_ENERGY_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val BASIC_ENERGY_OUTPUT_HATCH: BlockEntityEntry<BasicEnergyOutputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.BASIC_ENERGY_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_ENERGY_OUTPUT_HATCH: BlockEntityEntry<AdvancedEnergyOutputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.ADVANCED_ENERGY_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_ENERGY_OUTPUT_HATCH: BlockEntityEntry<IndustrialEnergyOutputHatchBlockEntity> =
        BlockEntityEntry.cast(
            AGPortBlocks.INDUSTRIAL_ENERGY_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    fun register() {}
}
