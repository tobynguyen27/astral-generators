package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial

import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class IndustrialInputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenus.INDUSTRIAL_INPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, IndustrialInputBusBlockEntity.CONTAINER_SIZE),
        null,
    ) {

    companion object {
        const val ID = "industrial_input_bus_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 20)

        val inputSlot = WItemSlot(blockInventory, 0, 5, 5, false)
        root.add(inputSlot, 6, 3)

        root.validate(this)
    }
}
