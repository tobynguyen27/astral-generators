package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.core.util.Identifier
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.BackgroundPainter
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.MenuType

open class MachineGUI(
    menuType: MenuType<*>,
    syncId: Int,
    playerInventory: Inventory,
    blockInventory: Container?,
    propertyDelegate: ContainerData?,
) : SyncedGuiDescription(menuType, syncId, playerInventory, blockInventory, propertyDelegate) {

    companion object {
        private val MACHINE_GUI =
            BackgroundPainter.createNinePatch(Identifier("textures/gui/machine_gui.png"))
    }

    override fun addPainters() {
        if (rootPanel != null && !fullscreen) {
            this.rootPanel.setBackgroundPainter(MACHINE_GUI)
        }
    }
}
