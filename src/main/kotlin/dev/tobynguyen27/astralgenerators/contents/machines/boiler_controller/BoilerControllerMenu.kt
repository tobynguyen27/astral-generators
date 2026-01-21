package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.core.network.Packets
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.data.config.ConfigHolder.CONFIG
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.gui.widgets.PowerButton
import dev.tobynguyen27.astralgenerators.gui.widgets.TemperatureBar
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

class BoilerControllerMenu(syncId: Int, playerInventory: Inventory, val ctx: ContainerLevelAccess) :
    MachineGUI(
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

        private const val MAX_HEAT_INDEX = 0
        private const val CURRENT_HEAT_INDEX = 1
        private const val BURN_TIME_INDEX = 3
        private const val IS_ENABLED_INDEX = 4
    }

    private val maxHeat
        get() = propertyDelegate.get(MAX_HEAT_INDEX)

    private val currentHeat
        get() = propertyDelegate.get(CURRENT_HEAT_INDEX)

    private val burnTime
        get() = propertyDelegate.get(BURN_TIME_INDEX)

    private val efficiency
        get() = currentHeat.toDouble() / maxHeat.toDouble()

    private val consumingAmount: Long
        get() {
            return if (currentHeat < 100) {
                0L
            } else {
                (efficiency * CONFIG.idealWaterConsumption).toLong()
            }
        }

    private val producingAmount: Long
        get() {
            return if (currentHeat < 100) {
                0L
            } else {
                (efficiency * CONFIG.idealWaterConsumption * CONFIG.steamExpansionRatio).toLong()
            }
        }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        // Widgets
        val temperatureBar = TemperatureBar({ maxHeat.toLong() }, { currentHeat.toLong() })

        val powerButton = PowerButton(IS_ENABLED_INDEX)
        powerButton.onToggle = {
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(Packets.TOGGLE_MACHINE) { packet ->
                packet.writeInt(it.toInt())
            }
        }
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(Packets.TOGGLE_MACHINE) { packet ->
            propertyDelegate.set(IS_ENABLED_INDEX, packet.readInt())
        }

        val burnTimeWidget = WDynamicLabel({ "Burn Time: $burnTime" })
        val consumingWidget =
            WDynamicLabel({
                "Consuming: ${
                    FormattingUtil.formatBuckets(consumingAmount)
                }/t"
            })
        val producingWidget =
            WDynamicLabel({
                "Producing: ${
                    FormattingUtil.formatBuckets(producingAmount)
                }/t"
            })
        val efficiencyWidget =
            WDynamicLabel({
                "Efficiency: ${FormattingUtil.formatPercent(
                    currentHeat,
                    maxHeat,
                    "0",
                )}"
            })

        with(root) {
            add(temperatureBar, 1, 2, 2, 12)
            add(efficiencyWidget, 5, 5)
            add(consumingWidget, 5, 7)
            add(producingWidget, 5, 9)
            add(burnTimeWidget, 5, 11)
            add(powerButton, 24, 11, 3, 3)
        }

        root.validate(this)
    }
}
