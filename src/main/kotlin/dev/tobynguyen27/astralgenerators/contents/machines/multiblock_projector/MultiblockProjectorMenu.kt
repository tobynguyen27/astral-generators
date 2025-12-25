package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.gui.MachineGUI
import dev.tobynguyen27.astralgenerators.registry.AGMenus
import io.github.cottonmc.cotton.gui.widget.WBox
import io.github.cottonmc.cotton.gui.widget.WButton
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WScrollPanel
import io.github.cottonmc.cotton.gui.widget.data.Axis
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import io.github.cottonmc.cotton.gui.widget.data.Insets
import java.io.File
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess

class MultiblockProjectorMenu(
    syncId: Int,
    playerInventory: Inventory,
    val ctx: ContainerLevelAccess,
) : MachineGUI(AGMenus.MULTIBLOCK_PROJECTOR, syncId, playerInventory, null, null) {

    private var availableDefinitions: MutableList<ResourceLocation> = mutableListOf()

    val root = WGridPanel(6)

    constructor(
        syncId: Int,
        playerInventory: Inventory,
        packet: FriendlyByteBuf,
    ) : this(syncId, playerInventory, ContainerLevelAccess.NULL) {
        val size = packet.readInt()

        repeat(size) {
            val value = packet.readResourceLocation()
            availableDefinitions.add(value)
        }

        val listPanel = WBox(Axis.VERTICAL)
        listPanel.spacing = 2

        availableDefinitions.forEach { res ->
            val button = WButton(TextComponent(FormattingUtil.toEnglishName(extractName(res.path))))
            button.alignment = HorizontalAlignment.CENTER
            button.setOnClick {
                // Code in this scope will be only run on client
                MultiblockProjectorBlockEntityRenderer.CURRENT_MULTIBLOCK = res
            }

            listPanel.add(button, 135, 20)
        }

        val scrollPanel = WScrollPanel(listPanel)
        scrollPanel.setScrollingHorizontally(TriState.FALSE)

        root.add(scrollPanel, 1, 2, 25, 12)
        root.validate(this)
    }

    companion object {
        const val ID = "multiblock_projector"

        private fun extractName(path: String): String {
            return File(path).nameWithoutExtension
        }
    }

    init {
        setRootPanel(root)

        // Base
        root.setInsets(Insets.ROOT_PANEL)
        root.add(createPlayerInventoryPanel(), 0, 15)

        root.validate(this)
    }
}
