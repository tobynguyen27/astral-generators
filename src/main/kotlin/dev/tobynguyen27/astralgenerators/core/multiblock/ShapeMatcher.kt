package dev.tobynguyen27.astralgenerators.core.multiblock

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.core.multiblock.level.ChunkEventListener
import dev.tobynguyen27.astralgenerators.core.multiblock.level.ChunkEventListeners
import it.unimi.dsi.fastutil.objects.*
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

open class ShapeMatcher(
    val level: Level,
    val controllerPos: BlockPos,
    val controllerDirection: Direction,
    val template: ShapeTemplate,
) : ChunkEventListener {

    val simpleMembers = toWorldPos(controllerPos, controllerDirection, template.simpleMembers)
    val portFlags = toWorldPos(controllerPos, controllerDirection, template.hatchFlags)

    private var needsRematch = true
    private var matchSuccessful = false
    private val matchedHatches = ObjectArrayList<PortBlockEntity>()

    fun registerListeners(level: Level) {
        getSpannedChunks().forEach { ChunkEventListeners.listeners.add(level, it, this) }
    }

    fun unregisterListeners(level: Level) {
        getSpannedChunks().forEach { ChunkEventListeners.listeners.remove(level, it, this) }
    }

    fun getSpannedChunks(): ObjectOpenHashSet<ChunkPos> {
        val spannedChunks = ObjectOpenHashSet<ChunkPos>()

        simpleMembers.keys.forEach { spannedChunks.add(ChunkPos(it)) }

        return spannedChunks
    }

    fun rematch(level: Level) {
        unlinkHatches()
        matchSuccessful = true

        simpleMembers.keys.forEach {
            if (!matches(it, level)) {
                matchSuccessful = false
            }
        }

        if (!checkRematch(level)) {
            matchSuccessful = false
        }

        if (!matchSuccessful) {
            matchedHatches.clear()
        } else {
            matchedHatches.forEach { it.link(template.casingBlock) }
        }

        needsRematch = false
    }

    protected fun checkRematch(world: Level): Boolean {
        return true
    }

    fun isMatchSuccessful(): Boolean {
        return matchSuccessful && !needsRematch
    }

    fun needsRematch(): Boolean {
        return needsRematch
    }

    fun matches(pos: BlockPos, level: Level): Boolean {
        val member = simpleMembers[pos] ?: return false
        val state = level.getBlockState(pos)

        if (member.matchesState(state)) {
            return true
        }

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is PortBlockEntity) {
            val flags = portFlags.get(pos)
            if (
                flags != null && flags.allows(blockEntity.getPortType()) && !blockEntity.isMatched()
            ) {
                matchedHatches.add(blockEntity)
                return true
            }
        }

        return false
    }

    fun unlinkHatches() {
        matchedHatches.forEach { it.unlink() }
        matchedHatches.clear()
        needsRematch = true
        matchSuccessful = false
    }

    fun getMatchedHatches(): ObjectList<PortBlockEntity> {
        return ObjectLists.unmodifiable(matchedHatches)
    }

    fun getHatchFlags(pos: BlockPos): PortFlags? {
        return portFlags.get(pos)
    }

    fun getSimpleMember(pos: BlockPos): SimpleMember {
        return simpleMembers.get(pos)!!
    }

    fun getPositions(): ObjectSet<BlockPos> {
        return simpleMembers.keys
    }

    override fun onBlockUpdate(pos: BlockPos) {
        if (simpleMembers.containsKey(pos)) {
            needsRematch = true
        }
    }

    override fun onUnload() {
        needsRematch = true
    }

    override fun onLoad() {
        needsRematch = true
    }

    companion object {
        fun toWorldPos(
            controllerPos: BlockPos,
            controllerDirection: Direction,
            templatePos: BlockPos,
        ): BlockPos {
            val rotatedPos =
                when (controllerDirection) {
                    Direction.NORTH -> templatePos
                    Direction.SOUTH -> BlockPos(-templatePos.x, templatePos.y, -templatePos.z)
                    Direction.EAST -> BlockPos(-templatePos.z, templatePos.y, templatePos.x)
                    else -> BlockPos(templatePos.z, templatePos.y, -templatePos.x)
                }
            return rotatedPos.offset(controllerPos)
        }

        fun <T> toWorldPos(
            controllerPos: BlockPos,
            controllerDirection: Direction,
            templateMap: Map<BlockPos, T>,
        ): Object2ObjectOpenHashMap<BlockPos, T> {
            val result = Object2ObjectOpenHashMap<BlockPos, T>()

            templateMap.entries.forEach { (key, value) ->
                result[toWorldPos(controllerPos, controllerDirection, key)] = value
            }

            return result
        }
    }
}
