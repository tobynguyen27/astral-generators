package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.MenuProvider

interface MenuProviderAttribute : Attribute, MenuProvider {
    override fun getDisplayName(): Component {
        return TranslatableComponent(self.blockState.block.descriptionId)
    }
}
