package dev.tobynguyen27.astralgenerators.contents.machines.assembler

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object AssemblerLogical {

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: AssemblerEntity,
    ) {}

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: AssemblerEntity,
    ) {
        if (blockEntity.CACHED_RECIPE === null) {
            updateActiveState(level, blockEntity, false)
            if (blockEntity.inputsStorage.all { it.isResourceBlank }) {
                return
            }
            startNewCrafting(level, blockEntity)
        } else {
            updateActiveState(level, blockEntity, true)
            continueCrafting(blockEntity, blockEntity.CACHED_RECIPE!!)
        }
    }

    private fun continueCrafting(blockEntity: AssemblerEntity, recipe: AssemblerRecipe) {
        if (consumeEnergy(blockEntity, recipe.energyConsumption.toLong())) {
            blockEntity.PROGRESS++

            if (blockEntity.PROGRESS == recipe.duration) {
                finishCrafting(blockEntity, recipe)
            }
        } else {
            blockEntity.PROGRESS = 0
        }
    }

    private fun finishCrafting(blockEntity: AssemblerEntity, recipe: AssemblerRecipe) {
        Transaction.openOuter().use { transaction ->
            if (produceItem(transaction, blockEntity, recipe)) {
                transaction.commit()
                blockEntity.CACHED_RECIPE = null
                blockEntity.PROGRESS = 0
            }
        }
    }

    private fun startNewCrafting(level: Level, blockEntity: AssemblerEntity) {
        val optionalRecipe =
            level.recipeManager.getRecipeFor(AssemblerRecipe.Type, blockEntity, level)

        if (!optionalRecipe.isPresent) {
            return
        }

        val recipe = optionalRecipe.get()

        Transaction.openOuter().use { transaction ->
            if (
                canConsumeEnergy(transaction, blockEntity, recipe.energyConsumption.toLong()) &&
                    consumeFluid(
                        transaction,
                        blockEntity,
                        recipe.fluidInput.fluid.amount,
                        recipe.fluidInput.fluid.type,
                    ) &&
                    consumeItems(transaction, blockEntity, recipe) &&
                    produceItem(transaction, blockEntity, recipe, true)
            ) {
                transaction.commit()
                blockEntity.CACHED_RECIPE = recipe
                blockEntity.PROGRESS = 0
                blockEntity.setChanged()
            }
        }
    }

    private fun produceItem(
        transaction: Transaction,
        blockEntity: AssemblerEntity,
        recipe: AssemblerRecipe,
        simulate: Boolean = false,
    ): Boolean {
        val outputItem = recipe.itemOutput.item
        val outputStorage = blockEntity.outputStorage

        if (simulate) {
            val addedAmount =
                outputStorage.simulateInsert(
                    ItemVariant.of(outputItem),
                    outputItem.count.toLong(),
                    transaction,
                )

            return addedAmount == outputItem.count.toLong()
        }

        val addedAmount =
            outputStorage.insert(ItemVariant.of(outputItem), outputItem.count.toLong(), transaction)

        return addedAmount == outputItem.count.toLong()
    }

    private fun consumeItems(
        transaction: Transaction,
        blockEntity: AssemblerEntity,
        recipe: AssemblerRecipe,
    ): Boolean {
        val itemInputs = recipe.itemInputs
        val inputsStorage = blockEntity.inputsStorage

        for (itemInput in itemInputs) {

            val ingredient = itemInput.item
            var neededAmount = itemInput.amount.toLong()

            for (inputStorage in inputsStorage) {
                if (neededAmount == 0L) {
                    break
                }
                if (inputStorage.isResourceBlank) {
                    continue
                }

                val resource = inputStorage.resource

                if (ingredient.test(resource.toStack())) {
                    val consumedAmount = inputStorage.extract(resource, neededAmount, transaction)

                    neededAmount -= consumedAmount
                }
            }

            if (neededAmount > 0) {
                return false
            }
        }

        return true
    }

    private fun consumeFluid(
        transaction: Transaction,
        blockEntity: AssemblerEntity,
        amountToConsume: Long,
        variantToConsume: FluidVariant,
    ): Boolean {
        val consumedAmount =
            blockEntity.fluidStorage.extract(variantToConsume, amountToConsume, transaction)

        return consumedAmount == amountToConsume
    }

    private fun consumeEnergy(blockEntity: AssemblerEntity, amountToConsume: Long): Boolean {
        Transaction.openOuter().use { transaction ->
            val consumedAmount = blockEntity.energyStorage.extract(amountToConsume, transaction)

            if (consumedAmount == amountToConsume) {
                transaction.commit()
                return true
            } else {
                transaction.abort()
                return false
            }
        }
    }

    private fun canConsumeEnergy(
        transaction: Transaction,
        blockEntity: AssemblerEntity,
        amountToConsume: Long,
    ): Boolean {
        transaction.openNested().use {
            val consumedAmount = blockEntity.energyStorage.extract(amountToConsume, it)

            return consumedAmount == amountToConsume
        }
    }

    private fun updateActiveState(level: Level, entity: AssemblerEntity, active: Boolean) {
        val currentState = level.getBlockState(entity.blockPos)

        if (currentState.getValue(Assembler.ACTIVE) == active) {
            return
        }

        val newState = currentState.setValue(Assembler.ACTIVE, active)
        level.setBlock(entity.blockPos, newState, 3)

        entity.setChanged()
    }
}
