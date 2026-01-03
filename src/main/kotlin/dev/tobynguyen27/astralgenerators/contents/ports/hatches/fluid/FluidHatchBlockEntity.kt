package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.FluidContainerAttribute
import dev.tobynguyen27.codebebelib.fluid.FluidUtils
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.entity.BlockEntity
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
    ExtendedScreenHandlerFactory,
    FluidContainerAttribute {

    override val self: BlockEntity = this
    override val fluidContainer: SingleVariantStorage<FluidVariant> =
        createFluidContainer(capacityInBucket * FluidUtils.B)

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeLong(fluidContainer.capacity)
        buf.writeLong(fluidContainer.amount)
        buf.writeNbt(fluidContainer.variant.toNbt())
    }
}
