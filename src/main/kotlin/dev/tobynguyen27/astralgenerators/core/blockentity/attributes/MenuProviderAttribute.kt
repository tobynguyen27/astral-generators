package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess

interface MenuProviderAttribute : Attribute, MenuProvider {

    val menuFactory: (Int, Inventory, ContainerLevelAccess) -> AbstractContainerMenu

    override fun getDisplayName(): Component =
        TranslatableComponent(self.blockState.block.descriptionId)

    override fun createMenu(
        syncId: Int,
        playerInventory: Inventory,
        player: Player,
    ): AbstractContainerMenu =
        menuFactory(
            syncId,
            playerInventory,
            ContainerLevelAccess.create(player.level, self.blockPos),
        )
}
