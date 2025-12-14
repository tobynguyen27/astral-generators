package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WDynamicLabel
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class BoilerControllerMenu(syncId: Int, playerInventory: Inventory, val ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenus.BOILER_CONTROLLER,
        syncId,
        playerInventory,
        null,
        getBlockPropertyDelegate(ctx, BoilerControllerBlockEntity.CONTAINER_DATA_SIZE),
    ) {

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf,
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {}

    companion object {
        const val ID = "boiler_controller_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        val temperature = WDynamicLabel({
            "Temperature: ${
                FormattingUtil.formatTemperature(
                    propertyDelegate.get(1)
                )
            }"
        })
        root.add(temperature, 0, 2)
    }
}
