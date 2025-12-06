package dev.tobynguyen27.astralgenerators.contents.ports

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class PortBlockScreen<T : SyncedGuiDescription>(
    menu: T,
    playerInventory: Inventory,
    title: Component,
) : CottonInventoryScreen<T>(menu, playerInventory, title)
