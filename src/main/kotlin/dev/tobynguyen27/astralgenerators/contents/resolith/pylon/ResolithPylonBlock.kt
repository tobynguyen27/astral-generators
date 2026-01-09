package dev.tobynguyen27.astralgenerators.contents.resolith.pylon

import com.google.common.collect.ImmutableSet
import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlock
import dev.tobynguyen27.codebebelib.raytracer.VoxelShapeCache
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

abstract class ResolithPylonBlock(properties: BlockBehaviour.Properties) :
    ResolithBlock(properties) {

    companion object {
        val SHAPE: VoxelShape = run {
            val builder = ImmutableSet.builder<VoxelShape>()

            builder.add(box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0))
            builder.add(box(3.0, 7.0, 5.0, 5.0, 9.0, 13.0))
            builder.add(box(11.0, 7.0, 3.0, 13.0, 9.0, 11.0))
            builder.add(box(5.0, 7.0, 11.0, 13.0, 9.0, 13.0))
            builder.add(box(3.0, 7.0, 3.0, 11.0, 9.0, 5.0))

            VoxelShapeCache.merge(builder.build())
        }
    }

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = SHAPE
}
