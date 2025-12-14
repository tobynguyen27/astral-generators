package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity.Companion.CONTAINER_DATA_SIZE
import dev.tobynguyen27.astralgenerators.core.base.MultiblockControllerBlockEntity
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import dev.tobynguyen27.astralgenerators.core.util.SIUtils
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BoilerControllerBlockEntity(
    type: BlockEntityType<BoilerControllerBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) :
    MultiblockControllerBlockEntity(type, blockPos, blockState),
    MenuProvider,
    ExtendedScreenHandlerFactory {

    companion object {
        const val CONTAINER_DATA_SIZE = 2

        private var HEAT_TAG = "heat"
    }

    var heat = SIUtils.STANDARD_TEMPERATURE
    var maxHeat = 873.15

    // NBT
    override fun saveAdditional(tag: CompoundTag) {
        tag.putDouble(HEAT_TAG, heat)
        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        heat = tag.getDouble(HEAT_TAG)
        super.load(tag)
    }

    // Menu
    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return BoilerControllerMenu(
            i,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }

    override fun writeScreenOpeningData(player: ServerPlayer, packet: FriendlyByteBuf) {
        packet.writeDouble(heat)
    }

    // Multiblock
    override fun getMultiblockShape(): ShapeTemplate {
        return BoilerMultiblock.SHAPE
    }
}
