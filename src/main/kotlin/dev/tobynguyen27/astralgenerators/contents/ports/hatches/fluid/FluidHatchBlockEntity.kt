package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.codebebelib.fluid.FluidUtils
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class FluidHatchBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    capacityInBucket: Int,
    tier: PortBlockSpecification.Tier,
    mode: PortBlockSpecification.Mode,
    casingBlock: BlockState?,
) :
    PortBlockEntity(blockEntityType, blockPos, blockState, tier, mode, casingBlock),
    ExtendedScreenHandlerFactory {

    companion object {
        private const val FLUID_STORAGE_AMOUNT_TAG = "fluid_amount"
        private const val FLUID_STORAGE_TYPE_TAG = "fluid_type"
    }

    val fluidStorage =
        object : SingleVariantStorage<FluidVariant>() {
            override fun getBlankVariant(): FluidVariant = FluidVariant.blank()

            override fun getCapacity(p0: FluidVariant): Long = capacityInBucket * FluidUtils.B

            override fun onFinalCommit() {
                setChanged()
            }
        }

    override fun saveAdditional(tag: CompoundTag) {
        tag.putLong(FLUID_STORAGE_AMOUNT_TAG, fluidStorage.amount)
        tag.put(FLUID_STORAGE_TYPE_TAG, fluidStorage.variant.toNbt())

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        fluidStorage.amount = tag.getLong(FLUID_STORAGE_AMOUNT_TAG)
        fluidStorage.variant = FluidVariant.fromNbt(tag.getCompound(FLUID_STORAGE_TYPE_TAG))

        super.load(tag)
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeLong(fluidStorage.capacity)
        buf.writeLong(fluidStorage.amount)
        buf.writeNbt(fluidStorage.variant.toNbt())
    }
}
