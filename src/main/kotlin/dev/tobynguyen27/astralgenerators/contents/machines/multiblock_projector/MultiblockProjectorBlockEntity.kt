package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.MenuProviderAttribute
import dev.tobynguyen27.astralgenerators.core.multiblock.pool.MultiblocksPool
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class MultiblockProjectorBlockEntity(
    type: BlockEntityType<MultiblockProjectorBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BlockEntity(type, blockPos, blockState), MenuProviderAttribute, ExtendedScreenHandlerFactory {

    override val self: BlockEntity = this
    override val menuFactory: (Int, Inventory, ContainerLevelAccess) -> AbstractContainerMenu =
        ::MultiblockProjectorMenu

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeInt(MultiblocksPool.DEFINITIONS.size)

        MultiblocksPool.DEFINITIONS.keys.forEach { buf.writeResourceLocation(it) }
    }
}
