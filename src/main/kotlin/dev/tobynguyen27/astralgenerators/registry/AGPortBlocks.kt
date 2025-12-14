package dev.tobynguyen27.astralgenerators.registry

import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced.AdvancedEnergyInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced.AdvancedEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic.BasicEnergyInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic.BasicEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.industrial.IndustrialEnergyInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.industrial.IndustrialEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced.AdvancedEnergyOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced.AdvancedEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic.BasicEnergyOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic.BasicEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial.IndustrialEnergyOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial.IndustrialEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.registry.helper.PortBlockRegistry

object AGPortBlocks {

    val BASIC_INPUT_BUS: BlockEntry<BasicInputBus> =
        PortBlockRegistry.registerBasicInputBlock(BasicInputBus.ID, ::BasicInputBus)
            .blockEntity { type, blockPos, blockState ->
                BasicInputBusBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val ADVANCED_INPUT_BUS: BlockEntry<AdvancedInputBus> =
        PortBlockRegistry.registerAdvancedInputBlock(AdvancedInputBus.ID, ::AdvancedInputBus)
            .blockEntity { type, blockPos, blockState ->
                AdvancedInputBusBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val INDUSTRIAL_INPUT_BUS: BlockEntry<IndustrialInputBus> =
        PortBlockRegistry.registerIndustrialInputBlock(IndustrialInputBus.ID, ::IndustrialInputBus)
            .blockEntity { type, blockPos, blockState ->
                IndustrialInputBusBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val BASIC_OUTPUT_BUS: BlockEntry<BasicOutputBus> =
        PortBlockRegistry.registerBasicOutputBlock(BasicOutputBus.ID, ::BasicOutputBus)
            .blockEntity { type, blockPos, blockState ->
                BasicOutputBusBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val ADVANCED_OUTPUT_BUS: BlockEntry<AdvancedOutputBus> =
        PortBlockRegistry.registerAdvancedOutputBlock(AdvancedOutputBus.ID, ::AdvancedOutputBus)
            .blockEntity { type, blockPos, blockState ->
                AdvancedOutputBusBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val INDUSTRIAL_OUTPUT_BUS: BlockEntry<IndustrialOutputBus> =
        PortBlockRegistry.registerIndustrialOutputBlock(
                IndustrialOutputBus.ID,
                ::IndustrialOutputBus,
            )
            .blockEntity { type, blockPos, blockState ->
                IndustrialOutputBusBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val BASIC_FLUID_INPUT_HATCH: BlockEntry<BasicFluidInputHatch> =
        PortBlockRegistry.registerBasicInputBlock(BasicFluidInputHatch.ID, ::BasicFluidInputHatch)
            .blockEntity { type, blockPos, blockState ->
                BasicFluidInputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val ADVANCED_FLUID_INPUT_HATCH: BlockEntry<AdvancedFluidInputHatch> =
        PortBlockRegistry.registerAdvancedInputBlock(
                AdvancedFluidInputHatch.ID,
                ::AdvancedFluidInputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                AdvancedFluidInputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val INDUSTRIAL_FLUID_INPUT_HATCH: BlockEntry<IndustrialFluidInputHatch> =
        PortBlockRegistry.registerIndustrialInputBlock(
                IndustrialFluidInputHatch.ID,
                ::IndustrialFluidInputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                IndustrialFluidInputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val BASIC_FLUID_OUTPUT_HATCH: BlockEntry<BasicFluidOutputHatch> =
        PortBlockRegistry.registerBasicOutputBlock(
                BasicFluidOutputHatch.ID,
                ::BasicFluidOutputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                BasicFluidOutputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val ADVANCED_FLUID_OUTPUT_HATCH: BlockEntry<AdvancedFluidOutputHatch> =
        PortBlockRegistry.registerAdvancedOutputBlock(
                AdvancedFluidOutputHatch.ID,
                ::AdvancedFluidOutputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                AdvancedFluidOutputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val INDUSTRIAL_FLUID_OUTPUT_HATCH: BlockEntry<IndustrialFluidOutputHatch> =
        PortBlockRegistry.registerIndustrialOutputBlock(
                IndustrialFluidOutputHatch.ID,
                ::IndustrialFluidOutputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                IndustrialFluidOutputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()

    val BASIC_ENERGY_INPUT_HATCH: BlockEntry<BasicEnergyInputHatch> =
        PortBlockRegistry.registerBasicInputBlock(BasicEnergyInputHatch.ID, ::BasicEnergyInputHatch)
            .blockEntity { type, blockPos, blockState ->
                BasicEnergyInputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val ADVANCED_ENERGY_INPUT_HATCH: BlockEntry<AdvancedEnergyInputHatch> =
        PortBlockRegistry.registerAdvancedInputBlock(
                AdvancedEnergyInputHatch.ID,
                ::AdvancedEnergyInputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                AdvancedEnergyInputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val INDUSTRIAL_ENERGY_INPUT_HATCH: BlockEntry<IndustrialEnergyInputHatch> =
        PortBlockRegistry.registerIndustrialInputBlock(
                IndustrialEnergyInputHatch.ID,
                ::IndustrialEnergyInputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                IndustrialEnergyInputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val BASIC_ENERGY_OUTPUT_HATCH: BlockEntry<BasicEnergyOutputHatch> =
        PortBlockRegistry.registerBasicOutputBlock(
                BasicEnergyOutputHatch.ID,
                ::BasicEnergyOutputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                BasicEnergyOutputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val ADVANCED_ENERGY_OUTPUT_HATCH: BlockEntry<AdvancedEnergyOutputHatch> =
        PortBlockRegistry.registerAdvancedOutputBlock(
                AdvancedEnergyOutputHatch.ID,
                ::AdvancedEnergyOutputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                AdvancedEnergyOutputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val INDUSTRIAL_ENERGY_OUTPUT_HATCH: BlockEntry<IndustrialEnergyOutputHatch> =
        PortBlockRegistry.registerIndustrialOutputBlock(
                IndustrialEnergyOutputHatch.ID,
                ::IndustrialEnergyOutputHatch,
            )
            .blockEntity { type, blockPos, blockState ->
                IndustrialEnergyOutputHatchBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()

    fun register() {}
}
