package dev.tobynguyen27.astralgenerators.contents.ports

import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
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

    override fun getUpdateTag(): CompoundTag {
        return saveWithoutMetadata()
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun saveAdditional(tag: CompoundTag) {
        if (casingBlock != null) {
            tag.put("casingBlock", NbtUtils.writeBlockState(casingBlock!!))
        }
        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        casingBlock =
            if (tag.contains("casingBlock")) {
                NbtUtils.readBlockState(tag.getCompound("casingBlock"))
            } else {
                null
            }

        if (level != null && level!!.isClientSide) {
            level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
        }

        super.load(tag)
    }

    abstract fun getPortType(): PortBlockType

    fun unlink() {
        this.casingBlock = null
        level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
    }

    fun link(casingBlock: BlockState) {
        this.casingBlock = casingBlock
        level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
    }

    fun isMatched(): Boolean {
        return casingBlock != null
    }
}
