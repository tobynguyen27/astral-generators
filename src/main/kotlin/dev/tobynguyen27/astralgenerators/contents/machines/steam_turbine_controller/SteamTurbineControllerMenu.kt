package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineLogic.calculateEnergyProduced
import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.data.config.ConfigHolder.CONFIG
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.PowerButton
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import dev.tobynguyen27.sense.util.PrimitiveUtils.toInt
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.WDynamicLabel
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class SteamTurbineControllerMenu(
    syncId: Int,
    playerInventory: Inventory,
    val ctx: ContainerLevelAccess,
) :
    MachineGUI(
        AGMenus.STEAM_TURBINE_CONTROLLER,
        syncId,
        playerInventory,
        null,
        getBlockPropertyDelegate(ctx, SteamTurbineControllerBlockEntity.CONTAINER_DATA_SIZE),
    ) {

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf,
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {}

    companion object {
        const val ID = "steam_turbine_controller_menu"

        private const val MAX_ROTOR_SPEED_INDEX = 0
        private const val ROTOR_SPEED_INDEX = 1
        private const val IS_ENABLED_INDEX = 2
    }

    private val maxRotorSpeed
        get() = propertyDelegate.get(MAX_ROTOR_SPEED_INDEX)

    private val rotorSpeed
        get() = propertyDelegate.get(ROTOR_SPEED_INDEX)

    private val energyGenerated
        get() = calculateEnergyProduced(rotorSpeed)

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        val powerButton = PowerButton(IS_ENABLED_INDEX)
        powerButton.onToggle = {
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(Packets.TOGGLE_MACHINE) { packet ->
                packet.writeInt(it.toInt())
            }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(Packets.TOGGLE_MACHINE) { packet ->
            propertyDelegate.set(IS_ENABLED_INDEX, packet.readInt())
        }

        val speedWidget = WDynamicLabel({ "Speed: $rotorSpeed RPM" })
        val consumingWidget =
            WDynamicLabel({
                "Consuming: ${
                    if(rotorSpeed > 0) FormattingUtil.formatBuckets(CONFIG.maxSteamIntake.toLong()) else  FormattingUtil.formatBuckets(0)
                }/t"
            })
        val generatingWidget = WDynamicLabel({ "Generating: $energyGenerated/t" })

        with(root) {
            add(speedWidget, 0, 2)
            add(consumingWidget, 0, 4)
            add(generatingWidget, 0, 6)
            add(powerButton, 24, 11, 3, 3)
        }

        root.validate(this)
    }
}
