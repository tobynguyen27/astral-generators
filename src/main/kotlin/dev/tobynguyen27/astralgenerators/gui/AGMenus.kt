package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerMenu
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerMenu
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry

object AGMenus {

    val ASSEMBLER_MENU: ExtendedScreenHandlerType<AssemblerMenu> =
        Registry.register(
            Registry.MENU,
            Identifier(AssemblerMenu.ID),
            ExtendedScreenHandlerType { syncId, inventory, buf ->
                AssemblerMenu(syncId, inventory, buf)
            },
        )

    val BOILER_CONTROLLER: ExtendedScreenHandlerType<BoilerControllerMenu> = Registry.register(
        Registry.MENU,
        Identifier(BoilerControllerMenu.ID),
        ExtendedScreenHandlerType { syncId, inventory, buf ->
            BoilerControllerMenu(syncId, inventory, buf)
        },
    )

    fun register() {}
}
