package dev.tobynguyen27.astralgenerators.contents.ports

import dev.tobynguyen27.astralgenerators.core.base.MachineBlockEntity
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class PortBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    val tier: PortBlockSpecification.Tier,
    val mode: PortBlockSpecification.Mode,
    var casingBlock: BlockState?,
) : MachineBlockEntity(blockEntityType, blockPos, blockState), RenderAttachmentBlockEntity {
    // Block model
    override fun getRenderAttachmentData(): PortBlockModelClientData {
        return PortBlockModelClientData(mode, tier, casingBlock)
    }

    override fun getUpdateTag(): CompoundTag {
        val tag = saveWithoutMetadata()
        if (casingBlock != null) {
            tag.put("casingBlock", NbtUtils.writeBlockState(casingBlock!!))
        }
        return tag
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
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
        setChanged()
        level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
    }

    fun link(casingBlock: BlockState) {
        this.casingBlock = casingBlock
        setChanged()
        level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
    }

    fun isMatched(): Boolean {
        return casingBlock != null
    }
}
