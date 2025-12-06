package dev.tobynguyen27.astralgenerators.contents

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatchBlockEntity
import net.minecraft.core.Registry

object AGBlockEntities {

    val ASSEMBLER: BlockEntityEntry<AssemblerBlockEntity> =
        BlockEntityEntry.cast<AssemblerBlockEntity>(
            AGBlocks.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val AM_CONTROLLER: BlockEntityEntry<AMControllerBlockEntity> =
        BlockEntityEntry.cast<AMControllerBlockEntity>(
            AGBlocks.AM_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val BOILER_CONTROLLER: BlockEntityEntry<BoilerControllerBlockEntity> =
        BlockEntityEntry.cast<BoilerControllerBlockEntity>(
            AGBlocks.BOILER_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    // Buses
    val BASIC_INPUT_BUS: BlockEntityEntry<BasicInputBusBlockEntity> =
        BlockEntityEntry.cast<BasicInputBusBlockEntity>(
            AGBlocks.BASIC_INPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_INPUT_BUS: BlockEntityEntry<AdvancedInputBusBlockEntity> =
        BlockEntityEntry.cast<AdvancedInputBusBlockEntity>(
            AGBlocks.ADVANCED_INPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_INPUT_BUS: BlockEntityEntry<IndustrialInputBusBlockEntity> =
        BlockEntityEntry.cast<IndustrialInputBusBlockEntity>(
            AGBlocks.INDUSTRIAL_INPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val BASIC_OUTPUT_BUS: BlockEntityEntry<BasicOutputBusBlockEntity> =
        BlockEntityEntry.cast<BasicOutputBusBlockEntity>(
            AGBlocks.BASIC_OUTPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_OUTPUT_BUS: BlockEntityEntry<AdvancedOutputBusBlockEntity> =
        BlockEntityEntry.cast<AdvancedOutputBusBlockEntity>(
            AGBlocks.ADVANCED_OUTPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_OUTPUT_BUS: BlockEntityEntry<IndustrialOutputBusBlockEntity> =
        BlockEntityEntry.cast<IndustrialOutputBusBlockEntity>(
            AGBlocks.INDUSTRIAL_OUTPUT_BUS.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val BASIC_FLUID_INPUT_HATCH: BlockEntityEntry<BasicFluidInputHatchBlockEntity> =
        BlockEntityEntry.cast<BasicFluidInputHatchBlockEntity>(
            AGBlocks.BASIC_FLUID_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_FLUID_INPUT_HATCH: BlockEntityEntry<AdvancedFluidInputHatchBlockEntity> =
        BlockEntityEntry.cast<AdvancedFluidInputHatchBlockEntity>(
            AGBlocks.ADVANCED_FLUID_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_FLUID_INPUT_HATCH: BlockEntityEntry<IndustrialFluidInputHatchBlockEntity> =
        BlockEntityEntry.cast<IndustrialFluidInputHatchBlockEntity>(
            AGBlocks.INDUSTRIAL_FLUID_INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val BASIC_FLUID_OUTPUT_HATCH: BlockEntityEntry<BasicFluidOutputHatchBlockEntity> =
        BlockEntityEntry.cast<BasicFluidOutputHatchBlockEntity>(
            AGBlocks.BASIC_FLUID_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val ADVANCED_FLUID_OUTPUT_HATCH: BlockEntityEntry<AdvancedFluidOutputHatchBlockEntity> =
        BlockEntityEntry.cast<AdvancedFluidOutputHatchBlockEntity>(
            AGBlocks.ADVANCED_FLUID_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )
    val INDUSTRIAL_FLUID_OUTPUT_HATCH: BlockEntityEntry<IndustrialFluidOutputHatchBlockEntity> =
        BlockEntityEntry.cast<IndustrialFluidOutputHatchBlockEntity>(
            AGBlocks.INDUSTRIAL_FLUID_OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    fun register() {}
}
