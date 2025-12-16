package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WDynamicLabel
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class BoilerControllerMenu(syncId: Int, playerInventory: Inventory, val ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
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
    }

    private val maxHeat get() = propertyDelegate.get(MAX_HEAT_INDEX)
    private val currentHeat get() = propertyDelegate.get(CURRENT_HEAT_INDEX)
    private val burnTime get() = propertyDelegate.get(BURN_TIME_INDEX)

    private val efficiency get() = currentHeat.toDouble() / maxHeat.toDouble()
    private val consumingAmount: Long get() {
        return if(currentHeat < 100) {
            0L
        } else {
            (efficiency * BoilerControllerBlockEntity.IDEAL_WATER_CONSUMPTION).toLong()
        }
    }
    private val producingAmount: Long get() {
        return if(currentHeat < 100) {
            0L
        } else {
            (efficiency * BoilerControllerBlockEntity.IDEAL_WATER_CONSUMPTION * BoilerControllerBlockEntity.STEAM_EXPANSION_RATIO).toLong()
        }
    }

    init {
        val root = WGridPanel(6)
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        root.add(  WDynamicLabel({
            "Temperature: ${
                FormattingUtil.formatTemperature(
                    currentHeat
                )
            }"
        }), 0, 2)

        val burnTime = WDynamicLabel({ "Burn Time: $burnTime" })
        root.add(burnTime, 0, 4)

        val consuming =
            WDynamicLabel({
                "Consuming: ${
                    FormattingUtil.formatBuckets(consumingAmount)
                }/t"
            })
        root.add(consuming, 0, 6)

        val producing =
            WDynamicLabel({
                "Producing: ${
                    FormattingUtil.formatBuckets(producingAmount)
                }/t"
            })
        root.add(producing, 0, 8)

        val efficiency =
            WDynamicLabel({
                "Efficiency: ${FormattingUtil.formatPercent(
                    currentHeat,
                    maxHeat,
                    "0",
                )}"
            })
        root.add(efficiency, 0, 10)

        root.validate(this)
    }
}
