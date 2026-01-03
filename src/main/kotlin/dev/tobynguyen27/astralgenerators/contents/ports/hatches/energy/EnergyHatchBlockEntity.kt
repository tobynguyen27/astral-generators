package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.EnergyContainerAttribute
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.reborn.energy.api.base.SimpleEnergyStorage

abstract class EnergyHatchBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    capacity: Long,
    tier: PortBlockSpecification.Tier,
    mode: PortBlockSpecification.Mode,
    casingBlock: BlockState?,
) :
    PortBlockEntity(blockEntityType, blockPos, blockState, tier, mode, casingBlock),
    ExtendedScreenHandlerFactory,
    EnergyContainerAttribute {

    override val self: BlockEntity = this
    override val energyContainer: SimpleEnergyStorage =
        createEnergyContainer(capacity, capacity, capacity)

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeLong(energyContainer.capacity)
        buf.writeLong(energyContainer.amount)
    }
}
