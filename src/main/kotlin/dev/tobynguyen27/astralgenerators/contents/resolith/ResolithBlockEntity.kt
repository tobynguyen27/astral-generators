package dev.tobynguyen27.astralgenerators.contents.resolith

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithAttribute
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import dev.tobynguyen27.codebebelib.vec.Vector3
import kotlin.math.max
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

abstract class ResolithBlockEntity(
    type: BlockEntityType<out ResolithBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BlockEntity(type, blockPos, blockState) {

    val connectedNodes = hashSetOf<BlockPos>()

    fun hasFreeSlot(): Boolean = connectedNodes.size < getMaxConnection()

    fun isConnectedTo(blockPos: BlockPos): Boolean = connectedNodes.contains(blockPos)

    fun removeConnection(node: BlockPos) {
        connectedNodes.remove(node)
        setChanged()

        level?.let {
            if (!it.isClientSide) {
                it.sendBlockUpdated(blockPos, blockState, blockState, 3)
                updateNetwork()
            }
        }
    }

    fun addConnection(node: BlockPos) {
        connectedNodes.add(node)
        setChanged()

        level?.let {
            if (!it.isClientSide) {
                it.sendBlockUpdated(blockPos, blockState, blockState, 3)
                updateNetwork()
            }
        }
    }

    fun updateNetwork() {
        val level = level ?: return
        if (level.isClientSide) return

        val visited = hashSetOf<BlockPos>()
        val queue = ArrayDeque<BlockPos>()

        queue.add(blockPos)
        visited.add(blockPos)

        while (!queue.isEmpty()) {
            val currentPos = queue.removeFirst()
            if (!level.isLoaded(currentPos)) continue

            val entity = level.getBlockEntity(currentPos)

            if (entity is ResolithTransceiverBlockEntity && entity.isSendEnergy) {
                entity.markNetworkDirty()
            }

            if (entity is ResolithBlockEntity) {
                for (neighbor in entity.connectedNodes) {
                    if (visited.add(neighbor)) {
                        queue.add(neighbor)
                    }
                }
            }
        }
    }

    fun getMaxConnectionRange(): Int =
        ResolithAttribute.getStats(getResolithType(), getResolithTier()).range

    fun getMaxConnection(): Int =
        ResolithAttribute.getStats(getResolithType(), getResolithTier()).maxConnections

    abstract fun getResolithTier(): ResolithTier

    abstract fun getResolithType(): ResolithType

    // NBT
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)

    override fun getUpdateTag(): CompoundTag = saveWithoutMetadata()

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

    companion object {
        fun disconnect(level: Level, first: BlockPos, second: BlockPos) {
            val firstNode = level.getBlockEntity(first)
            val secondNode = level.getBlockEntity(second)

            if (firstNode is ResolithBlockEntity) firstNode.removeConnection(second)
            if (secondNode is ResolithBlockEntity) secondNode.removeConnection(first)
        }

        fun attemptHandshake(first: ResolithBlockEntity, second: ResolithBlockEntity): Boolean {
            if (first.blockPos == second.blockPos) return false
            if (first.isConnectedTo(second.blockPos) || second.isConnectedTo(first.blockPos))
                return false
            if (!(first.hasFreeSlot() && second.hasFreeSlot())) return false

            val vector1 = Vector3.fromBlockPos(first.blockPos)
            val vector2 = Vector3.fromBlockPos(second.blockPos)
            val distance = vector1.distance(vector2)
            val validRange = max(first.getMaxConnectionRange(), second.getMaxConnectionRange())

            if (distance > validRange) return false

            first.addConnection(second.blockPos)
            second.addConnection(first.blockPos)
            return true
        }
    }
}
