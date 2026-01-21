package dev.tobynguyen27.astralgenerators.contents.machines.assembler

import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.EnergyBar
import dev.tobynguyen27.astralgenerators.gui.widgets.FluidBar
import dev.tobynguyen27.astralgenerators.gui.widgets.PowerButton
import dev.tobynguyen27.astralgenerators.gui.widgets.ProgressBar
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import dev.tobynguyen27.sense.util.PrimitiveUtils.toInt
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class AssemblerMenu(syncId: Int, playerInventory: Inventory, val ctx: ContainerLevelAccess) :
    MachineGUI(
        AGMenus.ASSEMBLER_MENU,
        syncId,
        playerInventory,
        getBlockInventory(ctx, AssemblerBlockEntity.CONTAINER_SIZE),
        getBlockPropertyDelegate(ctx, AssemblerBlockEntity.CONTAINER_DATA_SIZE),
    ) {

    // Client
    private var energyCapacity = 0L
    private var energyAmount = 0L

    private var fluidCapacity = 0L
    private var fluidAmount = 0L
    private var fluidVariant = FluidVariant.blank()

    // Server
    private var lastEnergyAmount = 0L

    private var lastFluidAmount = 0L
    private var lastFluidVariant = FluidVariant.blank()

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf,
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {
        energyCapacity = packet.readLong()
        energyAmount = packet.readLong()

        fluidCapacity = packet.readLong()
        fluidAmount = packet.readLong()
        fluidVariant = FluidVariant.fromNbt(packet.readNbt())
    }

    companion object {
        const val ID = "assembler_menu"

        private const val IS_ENABLED_INDEX = 2
    }

    init {
        val root =
            WGridPanel(6).apply {
                // Base
                this.setInsets(Insets.ROOT_PANEL)
                this.add(createPlayerInventoryPanel(), 0, 15)

                // Item slots
                val inputSlots = WItemSlot(blockInventory, 0, 3, 3, false)
                this.add(inputSlots, 8, 3)

                val outputSlot = WItemSlot(blockInventory, 9, 1, 1, true)
                outputSlot.isInsertingAllowed = false
                this.add(outputSlot, 23, 6)

                // Energy bar
                val energyBar = EnergyBar({ energyCapacity }, { energyAmount })
                this.add(energyBar, 0, 3, 3, 9)

                // Fluid tank
                val fluidTank = FluidBar({ fluidVariant }, { fluidCapacity }, { fluidAmount })
                this.add(fluidTank, 4, 3, 3, 9)

                // Progress bar
                val progressBar =
                    ProgressBar(
                        Identifier("textures/gui/widgets/widget_progress_empty.png"),
                        Identifier("textures/gui/widgets/widget_progress_full.png"),
                        1,
                        0,
                    )
                this.add(progressBar, 18, 6, 3, 3)

                val powerButton = PowerButton(IS_ENABLED_INDEX)
                powerButton.onToggle = {
                    sendPacketFromClient(Packets.TOGGLE_MACHINE) { buf ->
                        buf.writeInt(it.toInt())
                    }
                }
                this.add(powerButton, 24, 11, 3, 3)

                this.validate(this@AssemblerMenu)
            }
        setRootPanel(root)

        if (world.isClientSide) {
            receivePacketOnClient(Packets.ENERGY_AMOUNT) { energyAmount = it.readLong() }
            receivePacketOnClient(Packets.FLUID_AMOUNT) { fluidAmount = it.readLong() }
            receivePacketOnClient(Packets.FLUID_VARIANT) {
                fluidVariant = FluidVariant.fromPacket(it)
            }
        } else {
            receivePacketOnServer(Packets.TOGGLE_MACHINE) {
                propertyDelegate.set(IS_ENABLED_INDEX, it.readInt())
            }
            ctx.execute { world, blockPos ->
                val blockEntity =
                    world.getBlockEntity(blockPos) as? AssemblerBlockEntity ?: return@execute

                val energyAmount = blockEntity.energyContainer.amount
                this.lastEnergyAmount = energyAmount

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
                world.getBlockEntity(blockPos) as? AssemblerBlockEntity ?: return@execute

            val energyAmount = blockEntity.energyContainer.amount
            if (energyAmount != this.lastEnergyAmount) {
                this.lastEnergyAmount = energyAmount
                ScreenNetworking.of(this, NetworkSide.SERVER).send(Packets.ENERGY_AMOUNT) { packet
                    ->
                    packet.writeLong(energyAmount)
                }
            }

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
}
