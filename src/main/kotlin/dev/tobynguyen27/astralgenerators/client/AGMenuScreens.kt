package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerScreen
import dev.tobynguyen27.astralgenerators.gui.AGMenus
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screens.MenuScreens

@Environment(EnvType.CLIENT)
object AGMenuScreens {
    fun register() {
        MenuScreens.register(AGMenus.ASSEMBLER_MENU) { type, playerInventory, title ->
            AssemblerScreen(type, playerInventory, title)
        }
    }
}
