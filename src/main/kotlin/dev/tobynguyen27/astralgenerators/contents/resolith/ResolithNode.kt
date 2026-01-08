package dev.tobynguyen27.astralgenerators.contents.resolith

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.nbt.Tag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ResolithNode(
    type: BlockEntityType<out ResolithBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BlockEntity(type, blockPos, blockState) {

    companion object {
        fun disconnect(level: Level, first: BlockPos, second: BlockPos) {
            val firstNode = level.getBlockEntity(first)
            val secondNode = level.getBlockEntity(second)

            if (firstNode is ResolithNode) firstNode.removeConnection(second)
            if (secondNode is ResolithNode) secondNode.removeConnection(first)
        }

        fun attemptHandshake(first: ResolithNode, second: ResolithNode): Boolean {
            if (first.blockPos.equals(second.blockPos)) return false
            if (first.isConnectedTo(second.blockPos) || second.isConnectedTo(first.blockPos))
                return false
            if (!(first.hasFreeSlot() && second.hasFreeSlot())) return false

            // TODO: Distance check

            first.addConnection(second.blockPos)
            second.addConnection(first.blockPos)
            return true
        }
    }

    val connectedNodes = hashSetOf<BlockPos>()

    fun addConnection(node: BlockPos) {
        connectedNodes.add(node)
        setChanged()

        if (level != null && !level!!.isClientSide) {
            level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
        }
    }

    fun removeConnection(node: BlockPos) {
        connectedNodes.remove(node)
        setChanged()

        if (level != null && !level!!.isClientSide) {
            level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
        }
    }

    fun isConnectedTo(node: BlockPos): Boolean = connectedNodes.contains(node)

    fun hasFreeSlot(): Boolean = connectedNodes.size < getConnectionLimit()

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(): CompoundTag {
        return saveWithoutMetadata()
    }

    override fun saveAdditional(tag: CompoundTag) {
        val list = ListTag()
        connectedNodes.forEach { list.add(NbtUtils.writeBlockPos(it)) }
        tag.put("connectedNodes", list)

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        connectedNodes.clear()
        if (tag.contains("connectedNodes")) {
            val list = tag.getList("connectedNodes", Tag.TAG_COMPOUND.toInt())
            list.forEach { connectedNodes.add(NbtUtils.readBlockPos(it as CompoundTag)) }
        }

        super.load(tag)
    }

    abstract fun getConnectionLimit(): Int
}
