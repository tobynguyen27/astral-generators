package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic

import dev.tobynguyen27.astralgenerators.gui.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class BasicInputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenus.BASIC_INPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, BasicInputBusBlockEntity.CONTAINER_SIZE),
        null,
    ) {

    companion object {
        const val ID = "basic_input_bus"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        val inputSlot = WItemSlot(blockInventory, 0, 1, 1, false)
        root.add(inputSlot, 12, 7)

        root.validate(this)
    }
}
