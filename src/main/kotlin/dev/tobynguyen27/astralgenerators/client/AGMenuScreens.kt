package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerScreen
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerScreen
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockScreen
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

        registerBusBlockScreens()
    }

    private fun registerBusBlockScreens() {
        val screens =
            arrayOf(
                AGMenus.BASIC_INPUT_BUS,
                AGMenus.ADVANCED_INPUT_BUS,
                AGMenus.INDUSTRIAL_INPUT_BUS,
                AGMenus.BASIC_OUTPUT_BUS,
                AGMenus.ADVANCED_OUTPUT_BUS,
                AGMenus.INDUSTRIAL_OUTPUT_BUS,
            )

        for (screen in screens) {
            MenuScreens.register(screen) { type, playerInventory, title ->
                PortBlockScreen(type, playerInventory, title)
            }
        }
    }
}
