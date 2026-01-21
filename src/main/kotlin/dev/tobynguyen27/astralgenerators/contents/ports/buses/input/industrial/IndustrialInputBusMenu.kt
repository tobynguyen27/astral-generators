package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial

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

class IndustrialInputBusMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    MachineGUI(
        AGMenus.INDUSTRIAL_INPUT_BUS,
        syncId,
        playerInventory,
        getBlockInventory(ctx, IndustrialInputBusBlockEntity.CONTAINER_SIZE),
        getBlockPropertyDelegate(ctx, PortBlockEntity.CONTAINER_DATA_SIZE),
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

        val inputSlots = WItemSlot(blockInventory, 0, 5, 5, false)
        root.add(inputSlots, 6, 3)

        val autoImportButton =
            IOButton(IOButton.Type.ONLY_IMPORT, PortBlockEntity.AUTO_IMPORT_CONTAINER_INDEX)
        autoImportButton.onToggle = {
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(Packets.TOGGLE_AUTO_IMPORT) { packet
                ->
                packet.writeInt((it.toInt()))
            }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER)
            .receive(
                Packets.TOGGLE_AUTO_IMPORT,
                { packet ->
                    propertyDelegate.set(
                        PortBlockEntity.AUTO_IMPORT_CONTAINER_INDEX,
                        packet.readInt(),
                    )
                },
            )
        root.add(autoImportButton, 24, 15, 3, 3)

        root.validate(this)
    }
}
