package dev.tobynguyen27.astralgenerators.registry

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerMenu
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerMenu
import dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector.MultiblockProjectorMenu
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineControllerMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced.AdvancedEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic.BasicEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.industrial.IndustrialEnergyInputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced.AdvancedEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic.BasicEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial.IndustrialEnergyOutputHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatchMenu
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

object AGMenus {

    val ASSEMBLER_MENU: ExtendedScreenHandlerType<AssemblerMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AssemblerMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AssemblerMenu(syncId, inventory, buf)
            },
        )

    val BOILER_CONTROLLER: ExtendedScreenHandlerType<BoilerControllerMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BoilerControllerMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BoilerControllerMenu(syncId, inventory, buf)
            },
        )

    val STEAM_TURBINE_CONTROLLER: ExtendedScreenHandlerType<SteamTurbineControllerMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(SteamTurbineControllerMenu.ID),
            ExtendedScreenHandlerType(::SteamTurbineControllerMenu),
        )

    val MULTIBLOCK_PROJECTOR: ExtendedScreenHandlerType<MultiblockProjectorMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(MultiblockProjectorMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                MultiblockProjectorMenu(syncId, inventory, buf)
            },
        )

    val BASIC_INPUT_BUS: MenuType<BasicInputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicInputBusMenu.Companion.ID),
            MenuType { syncId, inventory ->
                BasicInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val ADVANCED_INPUT_BUS: MenuType<AdvancedInputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedInputBusMenu.Companion.ID),
            MenuType { syncId, inventory ->
                AdvancedInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val INDUSTRIAL_INPUT_BUS: MenuType<IndustrialInputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialInputBusMenu.Companion.ID),
            MenuType { syncId, inventory ->
                IndustrialInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val BASIC_OUTPUT_BUS: MenuType<BasicOutputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicOutputBusMenu.Companion.ID),
            MenuType { syncId, inventory ->
                BasicOutputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val ADVANCED_OUTPUT_BUS: MenuType<AdvancedOutputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedOutputBusMenu.Companion.ID),
            MenuType { syncId, inventory ->
                AdvancedOutputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val INDUSTRIAL_OUTPUT_BUS: MenuType<IndustrialOutputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialOutputBusMenu.Companion.ID),
            MenuType { syncId, inventory ->
                IndustrialOutputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val BASIC_FLUID_INPUT_HATCH: MenuType<BasicFluidInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicFluidInputHatchMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BasicFluidInputHatchMenu(syncId, inventory, buf)
            },
        )
    val ADVANCED_FLUID_INPUT_HATCH: MenuType<AdvancedFluidInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedFluidInputHatchMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AdvancedFluidInputHatchMenu(syncId, inventory, buf)
            },
        )
    val INDUSTRIAL_FLUID_INPUT_HATCH: MenuType<IndustrialFluidInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialFluidInputHatchMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                IndustrialFluidInputHatchMenu(syncId, inventory, buf)
            },
        )
    val BASIC_FLUID_OUTPUT_HATCH: MenuType<BasicFluidOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicFluidOutputHatchMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BasicFluidOutputHatchMenu(syncId, inventory, buf)
            },
        )
    val ADVANCED_FLUID_OUTPUT_HATCH: MenuType<AdvancedFluidOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedFluidOutputHatchMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AdvancedFluidOutputHatchMenu(syncId, inventory, buf)
            },
        )
    val INDUSTRIAL_FLUID_OUTPUT_HATCH: MenuType<IndustrialFluidOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialFluidOutputHatchMenu.Companion.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                IndustrialFluidOutputHatchMenu(syncId, inventory, buf)
            },
        )

    val BASIC_ENERGY_INPUT_HATCH: MenuType<EnergyHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier("basic_energy_input_hatch"),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                EnergyHatchMenu(
                    BasicEnergyInputHatchBlockEntity.MODE,
                    BASIC_ENERGY_INPUT_HATCH,
                    syncId,
                    inventory,
                    buf,
                )
            },
        )
    val ADVANCED_ENERGY_INPUT_HATCH: MenuType<EnergyHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier("advanced_energy_input_hatch"),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                EnergyHatchMenu(
                    AdvancedEnergyInputHatchBlockEntity.MODE,
                    ADVANCED_ENERGY_INPUT_HATCH,
                    syncId,
                    inventory,
                    buf,
                )
            },
        )
    val INDUSTRIAL_ENERGY_INPUT_HATCH: MenuType<EnergyHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier("industrial_energy_input_hatch"),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                EnergyHatchMenu(
                    IndustrialEnergyInputHatchBlockEntity.MODE,
                    INDUSTRIAL_ENERGY_INPUT_HATCH,
                    syncId,
                    inventory,
                    buf,
                )
            },
        )
    val BASIC_ENERGY_OUTPUT_HATCH: MenuType<EnergyHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier("basic_energy_output_hatch"),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                EnergyHatchMenu(
                    BasicEnergyOutputHatchBlockEntity.MODE,
                    BASIC_ENERGY_OUTPUT_HATCH,
                    syncId,
                    inventory,
                    buf,
                )
            },
        )
    val ADVANCED_ENERGY_OUTPUT_HATCH: MenuType<EnergyHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier("advanced_energy_output_hatch"),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                EnergyHatchMenu(
                    AdvancedEnergyOutputHatchBlockEntity.MODE,
                    ADVANCED_ENERGY_OUTPUT_HATCH,
                    syncId,
                    inventory,
                    buf,
                )
            },
        )
    val INDUSTRIAL_ENERGY_OUTPUT_HATCH: MenuType<EnergyHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier("industrial_energy_output_hatch"),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                EnergyHatchMenu(
                    IndustrialEnergyOutputHatchBlockEntity.MODE,
                    INDUSTRIAL_ENERGY_OUTPUT_HATCH,
                    syncId,
                    inventory,
                    buf,
                )
            },
        )

    fun register() {}
}
