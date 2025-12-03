package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.BusBlock
import dev.tobynguyen27.astralgenerators.contents.ports.BusBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class BasicInputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "basic_input_bus"
    }

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        isMoving: Boolean,
    ) {
        if (state.block != newState.block) {
            val blockEntity = level.getBlockEntity(pos)

            if (blockEntity is BusBlockEntity) {
                Containers.dropContents(level, pos, blockEntity.getItems())
                level.updateNeighbourForOutputSignal(pos, this)
            }
        }

        super.onRemove(state, level, pos, newState, isMoving)
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        if (!level.isClientSide) {
            player.openMenu(state.getMenuProvider(level, pos))
        }

        return InteractionResult.SUCCESS
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BASIC_INPUT_BUS.create(pos, state)
    }
}
