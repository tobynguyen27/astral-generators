package dev.tobynguyen27.astralgenerators.contents.resolith.providers

import dev.tobynguyen27.codebebelib.vec.Cuboid6
import dev.tobynguyen27.codebebelib.vec.Rotation
import dev.tobynguyen27.codebebelib.vec.Vector3
import java.util.EnumMap
import kotlin.collections.set
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block.box
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.Shapes.or
import net.minecraft.world.phys.shapes.VoxelShape

object ResolithVoxelShape {
    val RELAY_SHAPE: VoxelShape =
        listOf(
                box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0),
                box(3.0, 7.0, 5.0, 5.0, 9.0, 13.0),
                box(11.0, 7.0, 3.0, 13.0, 9.0, 11.0),
                box(5.0, 7.0, 11.0, 13.0, 9.0, 13.0),
                box(3.0, 7.0, 3.0, 11.0, 9.0, 5.0),
            )
            .fold(Shapes.empty(), ::or)

    val TRANSCEIVER_SHAPES =
        EnumMap<Direction, VoxelShape>(Direction::class.java).apply {
            val parts =
                listOf(
                    box(4.0, 0.0, 4.0, 12.0, 7.0, 12.0),
                    box(7.0, 7.0, 4.0, 9.0, 9.0, 14.0),
                    box(7.0, 0.0, 12.0, 9.0, 7.0, 14.0),
                    box(7.0, 0.0, 2.0, 9.0, 9.0, 4.0),
                    box(2.0, 7.0, 7.0, 7.0, 9.0, 9.0),
                    box(9.0, 7.0, 7.0, 12.0, 9.0, 9.0),
                    box(2.0, 0.0, 7.0, 4.0, 7.0, 9.0),
                    box(12.0, 0.0, 7.0, 14.0, 9.0, 9.0),
                )

            for (dir in Direction.entries) {
                val transform = Rotation.sideRotations[dir.ordinal].at(Vector3.CENTER)

                this[dir] =
                    parts.fold(Shapes.empty()) { acc, shape ->
                        val cuboid6 = Cuboid6(shape.bounds())
                        cuboid6.apply(transform)

                        Shapes.or(acc, cuboid6.shape())
                    }
            }
        }
}
