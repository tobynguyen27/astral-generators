package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial

import dev.tobynguyen27.astralgenerators.gui.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class IndustrialOutputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenus.INDUSTRIAL_OUTPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, IndustrialOutputBusBlockEntity.CONTAINER_SIZE),
        null,
    ) {

    companion object {
        const val ID = "industrial_output_bus_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 20)

        val inputSlots = WItemSlot(blockInventory, 0, 5, 5, false)
        inputSlots.isInsertingAllowed = false
        root.add(inputSlots, 6, 3)

        root.validate(this)
    }
}
