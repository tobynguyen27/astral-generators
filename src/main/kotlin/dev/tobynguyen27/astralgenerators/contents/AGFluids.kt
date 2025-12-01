package dev.tobynguyen27.astralgenerators.contents

import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.contents.tags.AGFluidTags
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.minecraft.tags.FluidTags as MCFluidTags

object AGFluids {
    val STEAM =
        REGISTRATE.fluid("steam", Identifier("fluid/steam"), Identifier("fluid/steam_flow"))
            .noBucket()
            .tag(AGFluidTags.STEAM)
            .removeTag(MCFluidTags.WATER)
            .register()

    fun register() {}
}
