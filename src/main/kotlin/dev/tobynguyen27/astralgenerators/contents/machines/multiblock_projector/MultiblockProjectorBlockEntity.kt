package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import dev.tobynguyen27.astralgenerators.multiblocks.pool.MultiblocksPool
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class MultiblockProjectorBlockEntity(
    type: BlockEntityType<MultiblockProjectorBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : BlockEntity(type, blockPos, blockState), MenuProvider, ExtendedScreenHandlerFactory {
    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return MultiblockProjectorMenu(
            i,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeInt(MultiblocksPool.DEFINITIONS.size)

        MultiblocksPool.DEFINITIONS.keys.forEach { buf.writeResourceLocation(it) }
    }
}
