package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import dev.tobynguyen27.sense.inventory.SenseInventory
import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack

interface InventoryAttribute: Attribute, SenseInventory {

    fun createInventory(size: Int) = NonNullList.withSize(size, ItemStack.EMPTY)

}
