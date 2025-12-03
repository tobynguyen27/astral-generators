package dev.tobynguyen27.astralgenerators.contents.machines.assembler

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AssemblerScreen(menu: AssemblerMenu, playerInventory: Inventory, title: Component) :
    CottonInventoryScreen<AssemblerMenu>(menu, playerInventory, title)
