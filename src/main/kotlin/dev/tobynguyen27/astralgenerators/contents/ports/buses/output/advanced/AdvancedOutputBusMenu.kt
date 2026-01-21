package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.IOButton
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import dev.tobynguyen27.sense.util.PrimitiveUtils.toInt
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class AdvancedOutputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    MachineGUI(
        AGMenus.ADVANCED_OUTPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, AdvancedOutputBusBlockEntity.CONTAINER_SIZE),
        getBlockPropertyDelegate(ctx, PortBlockEntity.CONTAINER_DATA_SIZE),
    ) {

    companion object {
        const val ID = "advanced_output_bus_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        val inputSlots = WItemSlot(blockInventory, 0, 3, 3, false)
        inputSlots.isInsertingAllowed = false
        root.add(inputSlots, 9, 4)

        val autoExportButton =
            IOButton(IOButton.Type.ONLY_EXPORT, PortBlockEntity.AUTO_EXPORT_CONTAINER_INDEX)
        autoExportButton.onToggle = {
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(Packets.TOGGLE_AUTO_EXPORT) { packet
                ->
                packet.writeInt((it.toInt()))
            }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER)
            .receive(
                Packets.TOGGLE_AUTO_EXPORT,
                { packet ->
                    propertyDelegate.set(
                        PortBlockEntity.AUTO_EXPORT_CONTAINER_INDEX,
                        packet.readInt(),
                    )
                },
            )
        root.add(autoExportButton, 24, 10, 3, 3)

        root.validate(this)
    }
}
