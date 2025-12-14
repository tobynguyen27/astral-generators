package dev.tobynguyen27.astralgenerators.core.multiblock

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class ShapeTemplate(val casingBlock: BlockState) {

    val simpleMembers = Object2ObjectOpenHashMap<BlockPos, SimpleMember>()
    val hatchFlags = Object2ObjectOpenHashMap<BlockPos, PortFlags>()

    class Builder(casingBlock: BlockState) {
        val template = ShapeTemplate(casingBlock)

        fun add3by3Levels(minY: Int, maxY: Int, member: SimpleMember, flags: PortFlags?): Builder {
            for (y in minY..maxY) {
                add3by3(y, member, y != minY, if (y == minY || y == maxY) flags else null)
            }

            return this
        }

        fun add3by3LevelsRoofed(
            minY: Int,
            maxY: Int,
            member: SimpleMember,
            flags: PortFlags?,
        ): Builder {
            for (y in minY..maxY) {
                add3by3(
                    y,
                    member,
                    y != minY && y != maxY,
                    if (y == minY || y == maxY) flags else null,
                )
            }

            return this
        }

        fun add3by3(y: Int, member: SimpleMember, hollow: Boolean, flags: PortFlags?): Builder {
            for (x in -1..1) {
                for (z in 0..2) {
                    if (hollow && x == 0 && z == 1) {
                        continue
                    }
                    add(x, y, z, member, flags)
                }
            }
            return this
        }

        fun add(x: Int, y: Int, z: Int, member: SimpleMember): Builder {
            add(x, y, z, member)

            return this
        }

        fun add(x: Int, y: Int, z: Int, member: SimpleMember, flags: PortFlags?): Builder {
            val pos = BlockPos(x, y, z)
            template.simpleMembers[pos] = member

            if (flags != null) {
                template.hatchFlags[pos] = flags
            }

            return this
        }

        fun remove(x: Int, y: Int, z: Int): Builder {
            val pos = BlockPos(x, y, z)
            template.simpleMembers.remove(pos)
            template.hatchFlags.remove(pos)

            return this
        }

        fun build(): ShapeTemplate {
            remove(0, 0, 0)
            return template
        }
    }
}
