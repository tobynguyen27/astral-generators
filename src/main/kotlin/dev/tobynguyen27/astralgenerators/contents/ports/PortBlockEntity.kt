package dev.tobynguyen27.astralgenerators.contents.ports

import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.MenuProvider
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class PortBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    val tier: PortBlockSpecification.Tier,
    val mode: PortBlockSpecification.Mode,
    var casingBlock: BlockState?,
) : BlockEntity(blockEntityType, blockPos, blockState), MenuProvider, RenderAttachmentBlockEntity {
    // Menu
    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    // Block model
    override fun getRenderAttachmentData(): PortBlockModelClientData {
        return PortBlockModelClientData(mode, tier, casingBlock)
    }
}
