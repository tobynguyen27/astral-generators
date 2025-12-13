package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerMenu
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerMenu
import dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector.MultiblockProjectorMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced.AdvancedEnergyInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic.BasicEnergyInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.industrial.IndustrialEnergyInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced.AdvancedEnergyOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic.BasicEnergyOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial.IndustrialEnergyOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatchMenu
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatchMenu
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

object AGMenus {

    val ASSEMBLER_MENU: ExtendedScreenHandlerType<AssemblerMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AssemblerMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AssemblerMenu(syncId, inventory, buf)
            },
        )

    val BOILER_CONTROLLER: ExtendedScreenHandlerType<BoilerControllerMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BoilerControllerMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BoilerControllerMenu(syncId, inventory, buf)
            },
        )

    val MULTIBLOCK_PROJECTOR: ExtendedScreenHandlerType<MultiblockProjectorMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(MultiblockProjectorMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                MultiblockProjectorMenu(syncId, inventory, buf)
            },
        )

    val BASIC_INPUT_BUS: MenuType<BasicInputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicInputBusMenu.ID),
            MenuType { syncId, inventory ->
                BasicInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val ADVANCED_INPUT_BUS: MenuType<AdvancedInputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedInputBusMenu.ID),
            MenuType { syncId, inventory ->
                AdvancedInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val INDUSTRIAL_INPUT_BUS: MenuType<IndustrialInputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialInputBusMenu.ID),
            MenuType { syncId, inventory ->
                IndustrialInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val BASIC_OUTPUT_BUS: MenuType<BasicOutputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicOutputBusMenu.ID),
            MenuType { syncId, inventory ->
                BasicOutputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val ADVANCED_OUTPUT_BUS: MenuType<AdvancedOutputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedOutputBusMenu.ID),
            MenuType { syncId, inventory ->
                AdvancedOutputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val INDUSTRIAL_OUTPUT_BUS: MenuType<IndustrialOutputBusMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialOutputBusMenu.ID),
            MenuType { syncId, inventory ->
                IndustrialOutputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val BASIC_FLUID_INPUT_HATCH: MenuType<BasicFluidInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicFluidInputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BasicFluidInputHatchMenu(syncId, inventory, buf)
            },
        )
    val ADVANCED_FLUID_INPUT_HATCH: MenuType<AdvancedFluidInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedFluidInputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AdvancedFluidInputHatchMenu(syncId, inventory, buf)
            },
        )
    val INDUSTRIAL_FLUID_INPUT_HATCH: MenuType<IndustrialFluidInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialFluidInputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                IndustrialFluidInputHatchMenu(syncId, inventory, buf)
            },
        )
    val BASIC_FLUID_OUTPUT_HATCH: MenuType<BasicFluidOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicFluidOutputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BasicFluidOutputHatchMenu(syncId, inventory, buf)
            },
        )
    val ADVANCED_FLUID_OUTPUT_HATCH: MenuType<AdvancedFluidOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedFluidOutputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AdvancedFluidOutputHatchMenu(syncId, inventory, buf)
            },
        )
    val INDUSTRIAL_FLUID_OUTPUT_HATCH: MenuType<IndustrialFluidOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialFluidOutputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                IndustrialFluidOutputHatchMenu(syncId, inventory, buf)
            },
        )

    val BASIC_ENERGY_INPUT_HATCH: MenuType<BasicEnergyInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicEnergyInputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BasicEnergyInputHatchMenu(syncId, inventory, buf)
            },
        )
    val ADVANCED_ENERGY_INPUT_HATCH: MenuType<AdvancedEnergyInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedEnergyInputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AdvancedEnergyInputHatchMenu(syncId, inventory, buf)
            },
        )
    val INDUSTRIAL_ENERGY_INPUT_HATCH: MenuType<IndustrialEnergyInputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialEnergyInputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                IndustrialEnergyInputHatchMenu(syncId, inventory, buf)
            },
        )
    val BASIC_ENERGY_OUTPUT_HATCH: MenuType<BasicEnergyOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(BasicEnergyOutputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                BasicEnergyOutputHatchMenu(syncId, inventory, buf)
            },
        )
    val ADVANCED_ENERGY_OUTPUT_HATCH: MenuType<AdvancedEnergyOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedEnergyOutputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AdvancedEnergyOutputHatchMenu(syncId, inventory, buf)
            },
        )
    val INDUSTRIAL_ENERGY_OUTPUT_HATCH: MenuType<IndustrialEnergyOutputHatchMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialEnergyOutputHatchMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                IndustrialEnergyOutputHatchMenu(syncId, inventory, buf)
            },
        )

    fun register() {}
}
