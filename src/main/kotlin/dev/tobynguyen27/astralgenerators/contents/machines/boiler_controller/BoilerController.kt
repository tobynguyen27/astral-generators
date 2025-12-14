package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.core.base.BlockWithEntity
import dev.tobynguyen27.astralgenerators.data.client.Texts
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult

class BoilerController(properties: BlockBehaviour.Properties) : BlockWithEntity(properties) {
    companion object {
        const val ID = "boiler_controller"

        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
        val LIT: BooleanProperty = BlockStateProperties.LIT
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(FACING, Direction.NORTH) })
        registerDefaultState(with(defaultBlockState()) { setValue(LIT, false) })
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, LIT)
    }

    override fun setPlacedBy(
        level: Level,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        stack: ItemStack,
    ) {
        if (placer !is Player) return

        level.setBlock(pos, level.getBlockState(pos).setValue(FACING, placer.direction.opposite), 3)
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        val blockEntity = level.getBlockEntity(pos)

        if (blockEntity is BoilerControllerBlockEntity) {
            if (!level.isClientSide) {

                if (blockEntity.isFormed) {
                    player.openMenu(state.getMenuProvider(level, pos))
                } else {
                    player.displayClientMessage(
                        TranslatableComponent(Texts.INVALID_MULTIBLOCK)
                            .withStyle(ChatFormatting.RED),
                        true,
                    )
                }
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun rotate(state: BlockState, rotation: Rotation): BlockState? {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BOILER_CONTROLLER.create(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>,
    ): BlockEntityTicker<T>? {
        if (level.isClientSide) {
            return createTickerHelper(
                blockEntityType,
                AGBlockEntities.BOILER_CONTROLLER.get(),
                BoilerControllerLogical::clientTick,
            )
        }

        return createTickerHelper(
            blockEntityType,
            AGBlockEntities.BOILER_CONTROLLER.get(),
            BoilerControllerLogical::serverTick,
        )
    }
}
