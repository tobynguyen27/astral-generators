package dev.tobynguyen27.astralgenerators.recipes

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.minecraft.core.Registry

object AGRecipes {

    fun register() {
        Registry.register(
            Registry.RECIPE_SERIALIZER,
            Identifier("assembler"),
            AssemblerRecipe.Serializer,
        )

        Registry.register(
            Registry.RECIPE_TYPE,
            Identifier(AssemblerRecipe.Type.ID),
            AssemblerRecipe.Type,
        )
    }
}
