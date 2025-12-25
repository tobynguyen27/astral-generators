package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.blocks.FireboxCasing
import dev.tobynguyen27.astralgenerators.core.base.MultiblockControllerBlockEntity
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BoilerControllerBlockEntity(
    type: BlockEntityType<BoilerControllerBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) :
    MultiblockControllerBlockEntity(type, blockPos, blockState),
    MenuProvider,
    ExtendedScreenHandlerFactory,
    PropertyDelegateHolder,
    AutoPersistBlockEntity {

    companion object {
        const val WATER_BOILING_POINT = 100
        const val STEAM_EXPANSION_RATIO = 160 // 1 water = 160 steam
        const val IDEAL_WATER_CONSUMPTION = 81 // Water consumed at 100% efficiency

        const val CONTAINER_DATA_SIZE = 5
    }

    private val managedFieldContainer by lazy { ManagedFieldContainer(this) }

    @Persisted var isEnabled = 0
    @Persisted var burnTime = 0
    @Persisted var maxBurnTime = 0
    @Persisted var heat = 0
    var maxHeat = 600

    // Menu
    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return BoilerControllerMenu(
            i,
            inventory,
            ContainerLevelAccess.create(player.level, blockPos),
        )
    }

    override fun writeScreenOpeningData(player: ServerPlayer, packet: FriendlyByteBuf) {}

    // Data
    val containerData =
        object : ContainerData {
            override fun get(index: Int): Int {
                return when (index) {
                    0 -> maxHeat
                    1 -> heat
                    2 -> maxBurnTime
                    3 -> burnTime
                    4 -> isEnabled
                    else -> -1
                }
            }

            override fun set(index: Int, value: Int) {
                when (index) {
                    1 -> heat = value
                    3 -> burnTime = value
                    4 -> isEnabled = value
                }
            }

            override fun getCount(): Int {
                return CONTAINER_DATA_SIZE
            }
        }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }

    // Multiblock
    override fun setRemoved() {
        val level = level

        if (level != null && !level.isClientSide) {
            updateFireboxActiveState(false)
        }

        super.setRemoved()
    }

    fun updateActiveState(active: Boolean) {
        val level = level ?: return
        val currentState = level.getBlockState(blockPos)

        if (currentState.getValue(BoilerController.LIT) == active) {
            return
        }

        val newState = currentState.setValue(BoilerController.LIT, active)
        level.setBlock(blockPos, newState, 3)
    }

    fun updateFireboxActiveState(active: Boolean) {
        val level = level ?: return
        val shapeMatcher = shapeMatcher ?: return

        shapeMatcher.simpleMembers.forEach { (blockPos, _) ->
            val currentState = level.getBlockState(blockPos)

            if (currentState.block is FireboxCasing) {
                if (currentState.getValue(FireboxCasing.LIT) != active) {
                    val newState = currentState.setValue(BoilerController.LIT, active)
                    level.setBlock(blockPos, newState, 3)
                }
            }
        }
    }

    override fun getMultiblockShape(): ShapeTemplate {
        return BoilerMultiblock.SHAPE
    }

    override fun getSelf(): BlockEntity = this

    override fun getFieldContainer(): ManagedFieldContainer = managedFieldContainer
}
