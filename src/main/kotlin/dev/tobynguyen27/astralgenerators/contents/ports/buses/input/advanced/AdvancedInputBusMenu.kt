package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced

import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class AdvancedInputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenus.ADVANCED_INPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, AdvancedInputBusBlockEntity.CONTAINER_SIZE),
        null,
    ) {

    companion object {
        const val ID = "advanced_input_bus_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        val inputSlots = WItemSlot(blockInventory, 0, 3, 3, false)
        root.add(inputSlots, 9, 4)

        root.validate(this)
    }
}
