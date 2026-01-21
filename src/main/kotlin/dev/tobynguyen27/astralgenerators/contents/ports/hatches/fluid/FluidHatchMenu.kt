package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.FluidBar
import dev.tobynguyen27.astralgenerators.gui.widgets.IOButton
import dev.tobynguyen27.sense.util.PrimitiveUtils.toInt
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

class FluidHatchMenu(
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
        fluidCapacity = buf.readLong()
        fluidAmount = buf.readLong()
        fluidVariant = FluidVariant.fromNbt(buf.readNbt())
    }

    // Client
    private var fluidCapacity = 0L
    private var fluidAmount = 0L
    private var fluidVariant = FluidVariant.blank()

    // Server
    private var lastFluidAmount = 0L
    private var lastFluidVariant = FluidVariant.blank()

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 11)

        val fluidTank = FluidBar({ fluidVariant }, { fluidCapacity }, { fluidAmount })
        root.add(fluidTank, 12, 2, 3, 9)

        addIOButton(root)

        root.validate(this)

        if (world.isClientSide) {
            receivePacketOnClient(Packets.FLUID_AMOUNT) { fluidAmount = it.readLong() }
            receivePacketOnClient(Packets.FLUID_VARIANT) {
                fluidVariant = FluidVariant.fromPacket(it)
            }
        } else {
            ctx.execute { world, blockPos ->
                val blockEntity =
                    world.getBlockEntity(blockPos) as? FluidHatchBlockEntity ?: return@execute

                val fluidAmount = blockEntity.fluidContainer.amount
                this.lastFluidAmount = fluidAmount
                val fluidVariant = blockEntity.fluidContainer.variant
                this.lastFluidVariant = fluidVariant
            }
        }
    }

    override fun broadcastChanges() {
        super.broadcastChanges()

        if (world.isClientSide) return

        ctx.execute { world, blockPos ->
            val blockEntity =
                world.getBlockEntity(blockPos) as? FluidHatchBlockEntity ?: return@execute

            val fluidAmount = blockEntity.fluidContainer.amount
            if (fluidAmount != this.lastFluidAmount) {
                this.lastFluidAmount = fluidAmount
                ScreenNetworking.of(this, NetworkSide.SERVER).send(Packets.FLUID_AMOUNT) { packet ->
                    packet.writeLong(fluidAmount)
                }
            }

            val fluidVariant = blockEntity.fluidContainer.variant
            if (fluidVariant != this.lastFluidVariant) {
                this.lastFluidVariant = fluidVariant
                ScreenNetworking.of(this, NetworkSide.SERVER).send(Packets.FLUID_VARIANT) { packet
                    ->
                    fluidVariant.toPacket(packet)
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
                packet.writeInt((it.toInt()))
            }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(togglePacket) { packet ->
            propertyDelegate.set(containerIndex, packet.readInt())
        }

        panel.add(button, 24, 8, 3, 3)
    }
}
