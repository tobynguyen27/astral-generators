package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.BusBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class IndustrialInputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "industrial_input_bus"
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
        return AGBlockEntities.INDUSTRIAL_INPUT_BUS.create(pos, state)
    }
}
