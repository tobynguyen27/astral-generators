package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ResolithTransceiverBlockEntity(
    type: BlockEntityType<out ResolithTransceiverBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithBlockEntity(type, blockPos, blockState), AutoPersistBlockEntity {

    @Persisted var isImport: Boolean = true

    override val self: BlockEntity = this
    override val fieldContainer: ManagedFieldContainer by lazy { ManagedFieldContainer(this) }

    override fun getResolithType(): ResolithType = ResolithType.TRANSCEIVER
}
