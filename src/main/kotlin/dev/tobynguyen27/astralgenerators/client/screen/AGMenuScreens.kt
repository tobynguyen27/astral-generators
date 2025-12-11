package dev.tobynguyen27.astralgenerators.client.screen

import dev.tobynguyen27.astralgenerators.gui.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.world.inventory.MenuType

typealias BasicScreenSet = ObjectOpenHashSet<MenuType<out SyncedGuiDescription>>

object AGMenuScreens {

    private val BASIC_SCREENS: BasicScreenSet =
        ObjectOpenHashSet.of(
            AGMenus.BASIC_INPUT_BUS,
            AGMenus.ADVANCED_INPUT_BUS,
            AGMenus.INDUSTRIAL_INPUT_BUS,
            AGMenus.BASIC_OUTPUT_BUS,
            AGMenus.ADVANCED_OUTPUT_BUS,
            AGMenus.INDUSTRIAL_OUTPUT_BUS,
            AGMenus.BASIC_FLUID_INPUT_HATCH,
            AGMenus.ADVANCED_FLUID_INPUT_HATCH,
            AGMenus.INDUSTRIAL_FLUID_INPUT_HATCH,
            AGMenus.BASIC_FLUID_OUTPUT_HATCH,
            AGMenus.ADVANCED_FLUID_OUTPUT_HATCH,
            AGMenus.INDUSTRIAL_FLUID_OUTPUT_HATCH,
            AGMenus.BASIC_ENERGY_INPUT_HATCH,
            AGMenus.ADVANCED_ENERGY_INPUT_HATCH,
            AGMenus.INDUSTRIAL_ENERGY_INPUT_HATCH,
            AGMenus.BASIC_ENERGY_OUTPUT_HATCH,
            AGMenus.ADVANCED_ENERGY_OUTPUT_HATCH,
            AGMenus.INDUSTRIAL_ENERGY_OUTPUT_HATCH,
            AGMenus.ASSEMBLER_MENU,
            AGMenus.BOILER_CONTROLLER,
        )

    fun register() {
        registerBasicScreens()
    }

    private fun registerBasicScreens() {
        BASIC_SCREENS.forEach { screen ->
            MenuScreens.register(screen) { type, playerInventory, title ->
                CottonInventoryScreen(type, playerInventory, title)
            }
        }
    }
}
