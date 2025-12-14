package dev.tobynguyen27.astralgenerators.registry

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.core.util.Identifier
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
