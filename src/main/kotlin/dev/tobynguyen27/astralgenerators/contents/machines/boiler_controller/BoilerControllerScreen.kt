package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BoilerControllerScreen(menu: BoilerControllerMenu, playerInventory: Inventory, title: Component) :
    CottonInventoryScreen<BoilerControllerMenu>(menu, playerInventory, title)
