package dev.tobynguyen27.astralgenerators.registry

import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.contents.tags.AGFluidTags
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import net.minecraft.tags.FluidTags

object AGFluids {
    val STEAM =
        REGISTRATE.fluid("steam", Identifier("fluid/steam"), Identifier("fluid/steam_flow"))
            .lang("Steam")
            .tag(AGFluidTags.STEAM)
            .removeTag(FluidTags.WATER)
            .register()

    fun register() {}
}
