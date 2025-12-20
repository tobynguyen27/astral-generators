package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.core.util.BooleanUtils
import dev.tobynguyen27.astralgenerators.gui.widgets.IOButton
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
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
        getBlockPropertyDelegate(ctx, PortBlockEntity.CONTAINER_DATA_SIZE),
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

        val autoImportButton =
            IOButton(IOButton.Type.ONLY_IMPORT, PortBlockEntity.AUTO_IMPORT_CONTAINER_INDEX)
        autoImportButton.onToggle = {
            ScreenNetworking.of(this, NetworkSide.CLIENT)
                .send(Packets.TOGGLE_AUTO_IMPORT) { packet ->
                    packet.writeInt(BooleanUtils.toInt(it))
                }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER)
            .receive(Packets.TOGGLE_AUTO_IMPORT, { packet ->
                propertyDelegate.set(PortBlockEntity.AUTO_IMPORT_CONTAINER_INDEX, packet.readInt())
            })
        root.add(autoImportButton, 24, 10, 3, 3)

        root.validate(this)
    }
}
