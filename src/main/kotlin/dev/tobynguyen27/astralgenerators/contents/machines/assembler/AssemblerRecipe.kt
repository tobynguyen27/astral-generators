package dev.tobynguyen27.astralgenerators.contents.machines.assembler

import com.google.gson.JsonObject
import dev.tobynguyen27.astralgenerators.recipe.base.FluidInput
import dev.tobynguyen27.astralgenerators.recipe.base.ItemInput
import dev.tobynguyen27.astralgenerators.recipe.base.ItemOutput
import io.github.fabricators_of_create.porting_lib.util.FluidStack
import java.util.ArrayList
import net.minecraft.core.Registry
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class AssemblerRecipe(
    val recipeId: ResourceLocation,
    val itemInputs: List<ItemInput>,
    val fluidInput: FluidInput,
    val itemOutput: ItemOutput,
    val energyConsumption: Int,
    val duration: Int,
) : Recipe<AssemblerBlockEntity> {
    override fun matches(container: AssemblerBlockEntity, level: Level): Boolean {
        if (container.energyStorage.amount < energyConsumption) {
            return false
        }

        if (container.fluidStorage.variant != fluidInput.fluid.type) {
            return false
        }
        if (container.fluidStorage.amount < fluidInput.fluid.amount) {
            return false
        }

        for (itemInput in itemInputs) {
            var foundAmount = 0

            repeat(container.containerSize - 1) { i ->
                val itemAtSlot = container.getItem(i)

                if (itemInput.item.test(itemAtSlot)) {
                    foundAmount += itemAtSlot.count
                }
            }

            if (foundAmount < itemInput.amount) {
                return false
            }
        }

        return true
    }

    override fun assemble(container: AssemblerBlockEntity): ItemStack {
        return resultItem.copy()
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return true
    }

    override fun getResultItem(): ItemStack {
        return itemOutput.item
    }

    override fun getId(): ResourceLocation {
        return recipeId
    }

    override fun getSerializer(): RecipeSerializer<AssemblerRecipe> {
        return Serializer
    }

    override fun getType(): RecipeType<AssemblerRecipe> {
        return Type
    }

    object Type : RecipeType<AssemblerRecipe> {
        const val ID = "assembler"
    }

    object Serializer : RecipeSerializer<AssemblerRecipe> {
        override fun fromJson(
            recipeId: ResourceLocation,
            serializedRecipe: JsonObject,
        ): AssemblerRecipe {

            val duration = GsonHelper.getAsInt(serializedRecipe, "duration", 1)
            val energyConsumption = GsonHelper.getAsInt(serializedRecipe, "energy", 1)

            val itemInputs =
                GsonHelper.getAsJsonArray(serializedRecipe, "item_inputs").map { jsonElement ->
                    val amount = GsonHelper.getAsInt(jsonElement.asJsonObject, "amount", 1)
                    val ingredient = Ingredient.fromJson(jsonElement)

                    ItemInput(ingredient, amount)
                }

            val fluidObject = GsonHelper.getAsJsonObject(serializedRecipe, "fluid_input")
            val fluidAmount = GsonHelper.getAsInt(fluidObject, "amount", 1)
            val fluidRegistry = Registry.FLUID.get(readIdentifier(fluidObject, "fluid"))
            val fluidStack = FluidStack(fluidRegistry, fluidAmount.toLong())
            val fluidInput = FluidInput(fluidStack)

            val itemOutputObject = GsonHelper.getAsJsonObject(serializedRecipe, "item_output")
            val itemOutputAmount = GsonHelper.getAsInt(itemOutputObject, "amount", 1)
            val itemOutputItem = Ingredient.fromJson(itemOutputObject).items.first().item
            val itemOutput = ItemOutput(ItemStack(itemOutputItem, itemOutputAmount))

            return AssemblerRecipe(
                recipeId,
                itemInputs,
                fluidInput,
                itemOutput,
                energyConsumption,
                duration,
            )
        }

        override fun fromNetwork(
            recipeId: ResourceLocation,
            buffer: FriendlyByteBuf,
        ): AssemblerRecipe {
            val duration = buffer.readVarInt()
            val energyConsumption = buffer.readVarInt()

            val itemOutput = ItemOutput(buffer.readItem())

            val fluidInput = FluidInput(FluidStack.fromBuffer(buffer))

            val itemInputsSize = buffer.readVarInt()
            val itemInputs = ArrayList<ItemInput>(itemInputsSize)
            repeat(itemInputsSize) {
                val amount = buffer.readVarInt()
                val item = Ingredient.fromNetwork(buffer)

                itemInputs.add(ItemInput(item, amount))
            }

            return AssemblerRecipe(
                recipeId,
                itemInputs,
                fluidInput,
                itemOutput,
                energyConsumption,
                duration,
            )
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AssemblerRecipe) {
            buffer.writeVarInt(recipe.duration)
            buffer.writeVarInt(recipe.energyConsumption)

            buffer.writeItem(recipe.itemOutput.item)

            FluidStack.toBuffer(recipe.fluidInput.fluid, buffer)

            buffer.writeVarInt(recipe.itemInputs.size)
            recipe.itemInputs.forEach { itemInput ->
                buffer.writeVarInt(itemInput.amount)
                itemInput.item.toNetwork(buffer)
            }
        }

        private fun readIdentifier(json: JsonObject, key: String): ResourceLocation {
            val value = GsonHelper.getAsString(json, key).split(":")

            return ResourceLocation(value[0], value[1])
        }
    }
}
