package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerMenu
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBusMenu
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBusMenu
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

    val BASIC_INPUT_BUS =
        Registry.register(
            Registry.MENU,
            Identifier(BasicInputBusMenu.ID),
            MenuType { syncId, inventory ->
                BasicInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val ADVANCED_INPUT_BUS =
        Registry.register(
            Registry.MENU,
            Identifier(AdvancedInputBusMenu.ID),
            MenuType { syncId, inventory ->
                AdvancedInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )
    val INDUSTRIAL_INPUT_BUS =
        Registry.register(
            Registry.MENU,
            Identifier(IndustrialInputBusMenu.ID),
            MenuType { syncId, inventory ->
                IndustrialInputBusMenu(syncId, inventory, ContainerLevelAccess.NULL)
            },
        )

    fun register() {}
}
