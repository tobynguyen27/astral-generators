package dev.tobynguyen27.astralgenerators.core.base

import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeMatcher
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties

abstract class MultiblockControllerBlockEntity(
    type: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
) : MachineBlockEntity(type, blockPos, blockState) {

    var shapeMatcher: ShapeMatcher? = null
    var isFormed = false

    fun createShapeMatcher(): ShapeMatcher {
        return ShapeMatcher(
            level!!,
            blockPos,
            blockState.getValue(BlockStateProperties.HORIZONTAL_FACING),
            getMultiblockShape(),
        )
    }

    protected open fun onRematch(shapeMatcher: ShapeMatcher) {}

    abstract fun getMultiblockShape(): ShapeTemplate

    fun link() {
        if (shapeMatcher == null) {
            shapeMatcher = createShapeMatcher()
            shapeMatcher!!.registerListeners(level!!)
        }
        if (shapeMatcher!!.needsRematch()) {
            isFormed = false
            shapeMatcher!!.rematch(level!!)
            onRematch(shapeMatcher!!)

            if (shapeMatcher!!.isMatchSuccessful()) {
                isFormed = true
            }
        }
    }

    fun unlink() {
        if (shapeMatcher == null) {
            return
        }

        shapeMatcher!!.unlinkHatches()
        shapeMatcher!!.unregisterListeners(level!!)
        shapeMatcher = null
    }

    override fun setRemoved() {
        super.setRemoved()
        val level = level

        if (level != null && !level.isClientSide) {
            unlink()
        }
    }

    override fun load(tag: CompoundTag) {
        isFormed = tag.getBoolean("isFormed")

        super.load(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        tag.putBoolean("isFormed", isFormed)

        super.saveAdditional(tag)
    }
}
