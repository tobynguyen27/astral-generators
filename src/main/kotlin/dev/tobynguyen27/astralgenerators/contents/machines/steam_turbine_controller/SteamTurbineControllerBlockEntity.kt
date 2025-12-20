package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.core.base.MultiblockControllerBlockEntity
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class SteamTurbineControllerBlockEntity(
    blockEntityType: BlockEntityType<SteamTurbineControllerBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) :
    MultiblockControllerBlockEntity(blockEntityType, blockPos, blockState),
    ExtendedScreenHandlerFactory,
    PropertyDelegateHolder {

    companion object {
        const val CONTAINER_DATA_SIZE = 3

        private const val IS_ENABLED_TAG = "is_enabled"
        private const val ROTOR_SPEED_TAG = "rotor_speed"
    }

    // Data
    var isEnabled = 0

    var rotorSpeed = 0
    val maxRotorSpeed = 3600

    override fun saveAdditional(tag: CompoundTag) {
        tag.putInt(ROTOR_SPEED_TAG, rotorSpeed)
        tag.putInt(IS_ENABLED_TAG, isEnabled)
        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        rotorSpeed = tag.getInt(ROTOR_SPEED_TAG)
        isEnabled = tag.getInt(IS_ENABLED_TAG)
        super.load(tag)
    }

    // Multiblock
    fun updateActiveState(active: Boolean) {
        val level = level ?: return
        val currentState = level.getBlockState(blockPos)

        if (currentState.getValue(SteamTurbineController.LIT) == active) {
            return
        }

        val newState = currentState.setValue(SteamTurbineController.LIT, active)
        level.setBlock(blockPos, newState, 3)
    }

    override fun getMultiblockShape(): ShapeTemplate {
        return SteamTurbineMultiblock.SHAPE
    }

    // Menu
    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return SteamTurbineControllerMenu(
            i,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {}

    val containerData =
        object : ContainerData {
            override fun get(index: Int): Int {
                return when (index) {
                    0 -> maxRotorSpeed
                    1 -> rotorSpeed
                    2 -> isEnabled
                    else -> -1
                }
            }

            override fun set(index: Int, value: Int) {
                when (index) {
                    1 -> rotorSpeed = value
                    2 -> isEnabled = value
                }
            }

            override fun getCount(): Int {
                return CONTAINER_DATA_SIZE
            }
        }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }
}
