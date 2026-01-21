package dev.tobynguyen27.astralgenerators.core.multiblock.pool

import dev.tobynguyen27.astralgenerators.AstralGenerators
import it.unimi.dsi.fastutil.chars.Char2ObjectMap
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

data class MultiblockDefinition(
    val origin: Origin,
    val keys: Char2ObjectMap<Block>,
    val pattern: List<List<String>>,
) {
    fun getBlocks(): HashMap<BlockPos, BlockState> {
        val map = hashMapOf<BlockPos, BlockState>()

        for ((yIndex, layers) in pattern.withIndex()) {
            for ((zIndex, row) in layers.withIndex()) {
                for ((xIndex, charKey) in row.withIndex()) {
                    if (charKey == ' ') {
                        continue
                    }

                    val blockViaChar =
                        keys[charKey]
                            ?: throw IllegalArgumentException(
                                "Undefined key in pattern: '$charKey'"
                            )
                    val block = Registry.BLOCK.get(ResourceLocation(blockViaChar.block))

                    if (block == Blocks.AIR) {
                        AstralGenerators.LOGGER.error("Block ${blockViaChar.block} not found")
                    }

                    // TODO: Handle custom blockstate here
                    val blockState = block.defaultBlockState()

                    val finalX = xIndex + origin.x
                    val finalY = yIndex + origin.y
                    val finalZ = zIndex + origin.z

                    map[BlockPos(finalX, finalY, finalZ)] = blockState
                }
            }
        }

        return map
    }
}

data class Block(val block: String, val nbt: String?)

data class Origin(val x: Int, val y: Int, val z: Int)
