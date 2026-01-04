package dev.tobynguyen27.astralgenerators.gui

import dev.tobynguyen27.astralgenerators.core.util.Identifier
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.BackgroundPainter
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.MenuType

open class MachineGUI(
    menuType: MenuType<*>,
    syncId: Int,
    playerInventory: Inventory,
    blockInventory: Container?,
    propertyDelegate: ContainerData?,
) : SyncedGuiDescription(menuType, syncId, playerInventory, blockInventory, propertyDelegate) {

    fun sendPacketFromClient(name: ResourceLocation, writer: (buf: FriendlyByteBuf) -> Unit) {
        ScreenNetworking.of(this, NetworkSide.CLIENT).send(name, writer)
    }

    fun sendPacketFromServer(name: ResourceLocation, writer: (buf: FriendlyByteBuf) -> Unit) {
        ScreenNetworking.of(this, NetworkSide.SERVER).send(name, writer)
    }

    fun receivePacketOnClient(name: ResourceLocation, action: (buf: FriendlyByteBuf) -> Unit) {
        ScreenNetworking.of(this, NetworkSide.CLIENT).receive(name, action)
    }

    fun receivePacketOnServer(name: ResourceLocation, action: (buf: FriendlyByteBuf) -> Unit) {
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(name, action)
    }

    @Environment(EnvType.CLIENT)
    override fun addPainters() {
        if (rootPanel != null && !fullscreen) {
            this.rootPanel.setBackgroundPainter(
                BackgroundPainter.createNinePatch(Identifier("textures/gui/machine_gui.png"))
            )
        }
    }
}
