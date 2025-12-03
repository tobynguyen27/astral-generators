package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerScreen
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerScreen
import dev.tobynguyen27.astralgenerators.contents.ports.BusBlockScreen
import dev.tobynguyen27.astralgenerators.gui.AGMenus
import net.minecraft.client.gui.screens.MenuScreens

object AGMenuScreens {
    fun register() {
        MenuScreens.register(AGMenus.ASSEMBLER_MENU) { type, playerInventory, title ->
            AssemblerScreen(type, playerInventory, title)
        }
        MenuScreens.register(AGMenus.BOILER_CONTROLLER) { type, playerInventory, title ->
            BoilerControllerScreen(type, playerInventory, title)
        }
        MenuScreens.register(AGMenus.BASIC_INPUT_BUS) { type, playerInventory, title ->
            BusBlockScreen(type, playerInventory, title)
        }
    }
}
