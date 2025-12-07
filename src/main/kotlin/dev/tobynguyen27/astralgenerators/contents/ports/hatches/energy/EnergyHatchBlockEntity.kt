package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
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
    ExtendedScreenHandlerFactory {

    companion object {
        private const val ENERGY_STORAGE_AMOUNT_TAG = "energy_amount"
    }

    val energyStorage =
        object :
            SimpleEnergyStorage(
                capacity,
                if (mode == PortBlockSpecification.Mode.INPUT) {
                    capacity
                } else {
                    0
                },
                if (mode == PortBlockSpecification.Mode.OUTPUT) {
                    capacity
                } else {
                    0
                },
            ) {

            override fun onFinalCommit() {
                setChanged()
            }
        }

    override fun saveAdditional(tag: CompoundTag) {
        tag.putLong(ENERGY_STORAGE_AMOUNT_TAG, energyStorage.amount)

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        energyStorage.amount = tag.getLong(ENERGY_STORAGE_AMOUNT_TAG)

        super.load(tag)
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeLong(energyStorage.capacity)
        buf.writeLong(energyStorage.amount)
    }
}
