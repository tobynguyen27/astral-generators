package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.FluidBar
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class IndustrialFluidInputHatchMenu(
    syncId: Int,
    playerInventory: Inventory,
    val ctx: ContainerLevelAccess,
) :
    MachineGUI(
        AGMenus.INDUSTRIAL_FLUID_INPUT_HATCH,
        syncId,
        playerInventory,
        null,
        null,
    ) {

    // Client
    private var fluidCapacity = 0L
    private var fluidAmount = 0L
    private var fluidVariant = FluidVariant.blank()

    // Server
    private var lastFluidAmount = 0L
    private var lastFluidVariant = FluidVariant.blank()

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf,
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {
        fluidCapacity = packet.readLong()
        fluidAmount = packet.readLong()
        fluidVariant = FluidVariant.fromNbt(packet.readNbt())
    }

    companion object {
        const val ID = "industrial_fluid_input_hatch_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 11)

        val fluidTank = FluidBar({ fluidVariant }, { fluidCapacity }, { fluidAmount })
        root.add(fluidTank, 12, 2, 3, 9)

        root.validate(this)

        if (world.isClientSide) {
            ScreenNetworking.of(this, NetworkSide.CLIENT).receive(Packets.FLUID_AMOUNT) { packet ->
                fluidAmount = packet.readLong()
            }
            ScreenNetworking.of(this, NetworkSide.CLIENT).receive(Packets.FLUID_VARIANT) { packet ->
                fluidVariant = FluidVariant.fromPacket(packet)
            }
        }

        if (!world.isClientSide) {
            ctx.execute { world, blockPos ->
                val blockEntity =
                    world.getBlockEntity(blockPos) as? FluidHatchBlockEntity ?: return@execute

                val fluidAmount = blockEntity.fluidStorage.amount
                this.lastFluidAmount = fluidAmount
                val fluidVariant = blockEntity.fluidStorage.variant
                this.lastFluidVariant = fluidVariant
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
                world.getBlockEntity(blockPos) as? FluidHatchBlockEntity ?: return@execute

            val fluidAmount = blockEntity.fluidStorage.amount
            if (fluidAmount != this.lastFluidAmount) {
                this.lastFluidAmount = fluidAmount
                ScreenNetworking.of(this, NetworkSide.SERVER).send(Packets.FLUID_AMOUNT) { packet ->
                    packet.writeLong(fluidAmount)
                }
            }

            val fluidVariant = blockEntity.fluidStorage.variant
            if (fluidVariant != this.lastFluidVariant) {
                this.lastFluidVariant = fluidVariant
                ScreenNetworking.of(this, NetworkSide.SERVER).send(Packets.FLUID_VARIANT) { packet
                    ->
                    fluidVariant.toPacket(packet)
                }
            }
        }
    }
}
