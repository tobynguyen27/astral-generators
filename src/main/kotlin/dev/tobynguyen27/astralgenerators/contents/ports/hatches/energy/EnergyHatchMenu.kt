package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.core.util.BooleanUtils
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.EnergyBar
import dev.tobynguyen27.astralgenerators.gui.widgets.IOButton
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

class EnergyHatchMenu(
    val mode: PortBlockSpecification.Mode,
    menuType: MenuType<*>,
    syncId: Int,
    playerInventory: Inventory,
    val ctx: ContainerLevelAccess,
) :
    MachineGUI(
        menuType,
        syncId,
        playerInventory,
        null,
        getBlockPropertyDelegate(ctx, PortBlockEntity.CONTAINER_DATA_SIZE),
    ) {

    constructor(
        mode: PortBlockSpecification.Mode,
        menuType: MenuType<*>,
        syncId: Int,
        playerInventory: Inventory,
        buf: FriendlyByteBuf,
    ) : this(mode, menuType, syncId, playerInventory, ContainerLevelAccess.NULL) {
        energyCapacity = buf.readLong()
        energyAmount = buf.readLong()
    }

    // Client
    private var energyCapacity = 0L
    private var energyAmount = 0L

    // Server
    private var lastEnergyAmount = 0L

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 11)

        val energyBar = EnergyBar({ energyCapacity }, { energyAmount })
        root.add(energyBar, 12, 2, 3, 9)

        addIOButton(root)

        root.validate(this)

        if (world.isClientSide) {
            ScreenNetworking.of(this, NetworkSide.CLIENT).receive(Packets.ENERGY_AMOUNT) { packet ->
                energyAmount = packet.readLong()
            }
        }

        if (!world.isClientSide) {
            ctx.execute { world, blockPos ->
                val blockEntity =
                    world.getBlockEntity(blockPos) as? EnergyHatchBlockEntity ?: return@execute

                val energyAmount = blockEntity.energyStorage.amount
                this.lastEnergyAmount = energyAmount
            }
        }
    }

    override fun broadcastChanges() {
        super.broadcastChanges()

        if (world.isClientSide) {
            return
        }

        ctx.execute { world, blockPos ->
            val blockEntity =
                world.getBlockEntity(blockPos) as? EnergyHatchBlockEntity ?: return@execute

            val energyAmount = blockEntity.energyStorage.amount
            if (energyAmount != this.lastEnergyAmount) {
                this.lastEnergyAmount = energyAmount
                ScreenNetworking.of(this, NetworkSide.SERVER).send(Packets.ENERGY_AMOUNT) { packet
                    ->
                    packet.writeLong(energyAmount)
                }
            }
        }
    }

    private fun addIOButton(panel: WGridPanel) {
        val (buttonType, containerIndex, togglePacket) =
            if (mode == PortBlockSpecification.Mode.INPUT) {
                Triple(
                    IOButton.Type.ONLY_IMPORT,
                    PortBlockEntity.AUTO_IMPORT_CONTAINER_INDEX,
                    Packets.TOGGLE_AUTO_IMPORT,
                )
            } else {
                Triple(
                    IOButton.Type.ONLY_EXPORT,
                    PortBlockEntity.AUTO_EXPORT_CONTAINER_INDEX,
                    Packets.TOGGLE_AUTO_EXPORT,
                )
            }

        val button = IOButton(buttonType, containerIndex)
        button.onToggle = {
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(togglePacket) { packet ->
                packet.writeInt(BooleanUtils.toInt(it))
            }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER)
            .receive(
                togglePacket,
            ) { packet -> propertyDelegate.set(containerIndex, packet.readInt()) }

        panel.add(button, 24, 8, 3, 3)
    }
}
