package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.iron

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntityLogic
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiver
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class IronResolithTransceiver(properties: Properties) : ResolithTransceiver(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        AGBlockEntities.IRON_RESOLITH_TRANSCEIVER.create(pos, state)

    override fun getResolithTier(): ResolithTier = ResolithTier.IRON

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>,
    ): BlockEntityTicker<T>? {
        if (level.isClientSide) return null

        return createTickerHelper(
            blockEntityType,
            AGBlockEntities.IRON_RESOLITH_TRANSCEIVER.get(),
            ResolithBlockEntityLogic::serverTick,
        )
    }
}
