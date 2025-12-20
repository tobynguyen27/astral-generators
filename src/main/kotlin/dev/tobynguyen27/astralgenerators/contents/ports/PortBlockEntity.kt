package dev.tobynguyen27.astralgenerators.contents.ports

import dev.tobynguyen27.astralgenerators.core.base.MachineBlockEntity
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class PortBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    val tier: PortBlockSpecification.Tier,
    val mode: PortBlockSpecification.Mode,
    var casingBlock: BlockState?,
) : MachineBlockEntity(blockEntityType, blockPos, blockState), RenderAttachmentBlockEntity, PropertyDelegateHolder {

    companion object {
        const val CONTAINER_DATA_SIZE = 2
        const val AUTO_EXPORT_CONTAINER_INDEX = 0
        const val AUTO_IMPORT_CONTAINER_INDEX = 1
    }

    var autoExport = 1
    var autoImport = 1

    // Block model
    override fun getRenderAttachmentData(): PortBlockModelClientData {
        return PortBlockModelClientData(mode, tier, casingBlock)
    }

    val containerData =
        object : ContainerData {
            override fun get(index: Int): Int {
                return when (index) {
                    AUTO_IMPORT_CONTAINER_INDEX -> autoImport
                    AUTO_EXPORT_CONTAINER_INDEX -> autoExport
                    else -> -1
                }
            }

            override fun set(index: Int, value: Int) {
                when (index) {
                    AUTO_IMPORT_CONTAINER_INDEX -> autoImport = value
                    AUTO_EXPORT_CONTAINER_INDEX -> autoExport = value
                }
            }

            override fun getCount(): Int {
                return CONTAINER_DATA_SIZE
            }
        }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
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

    override fun saveAdditional(tag: CompoundTag) {
        tag.putInt("autoImport", autoImport)
        tag.putInt("autoExport", autoExport)

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        autoImport = tag.getInt("autoImport")
        autoExport = tag.getInt("autoExport")

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

    // Multiblock
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
