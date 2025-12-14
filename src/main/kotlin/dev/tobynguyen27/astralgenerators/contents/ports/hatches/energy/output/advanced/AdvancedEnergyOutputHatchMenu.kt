package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlockEntity
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.gui.widgets.EnergyBar
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class AdvancedEnergyOutputHatchMenu(
    syncId: Int,
    playerInventory: Inventory,
    val ctx: ContainerLevelAccess,
) :
    SyncedGuiDescription(
        AGMenus.ADVANCED_ENERGY_OUTPUT_HATCH,
        syncId,
        playerInventory,
        null,
        null,
    ) {

    // Client
    private var energyCapacity = 0L
    private var energyAmount = 0L

    // Server
    private var lastEnergyAmount = 0L

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf,
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {
        energyCapacity = packet.readLong()
        energyAmount = packet.readLong()
    }

    companion object {
        const val ID = "advanced_energy_output_hatch_menu"
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 11)

        val energyBar = EnergyBar({ energyCapacity }, { energyAmount })
        root.add(energyBar, 12, 2, 3, 9)

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
}
