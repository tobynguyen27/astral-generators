package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic

import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class BasicOutputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenus.BASIC_OUTPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, BasicOutputBusBlockEntity.CONTAINER_SIZE),
        null,
    ) {

    companion object {
        const val ID = "basic_output_bus_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        val outputSlot = WItemSlot(blockInventory, 0, 1, 1, false)
        outputSlot.isInsertingAllowed = false
        root.add(outputSlot, 12, 7)

        root.validate(this)
    }
}
