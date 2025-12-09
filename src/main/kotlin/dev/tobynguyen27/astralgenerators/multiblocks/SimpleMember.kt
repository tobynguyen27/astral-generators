package dev.tobynguyen27.astralgenerators.multiblocks

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

interface SimpleMember {

    fun matchesState(state: BlockState): Boolean

    fun getPreviewState(): BlockState

    companion object {
        fun forBlock(block: Block): SimpleMember {
            return object : SimpleMember {
                override fun getPreviewState(): BlockState {
                    return block.defaultBlockState()
                }

                override fun matchesState(state: BlockState): Boolean {
                    return state.`is`(block)
                }
            }
        }

        fun forBlockState(state: BlockState): SimpleMember {
            return object : SimpleMember {
                override fun getPreviewState(): BlockState {
                    return state
                }

                override fun matchesState(state2: BlockState): Boolean {
                    return state == state2
                }
            }
        }

        fun forBlock(id: ResourceLocation): SimpleMember {
            return forBlock(Registry.BLOCK.get(id))
        }
    }
}
