package dev.tobynguyen27.astralgenerators.contents.tags

import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.core.util.TagUtils
import net.minecraft.world.level.block.Block

object AGBlockTags {
    val CASING = create("casings")
    val COIL = create("coils")

    private fun create(name: String) =
        dev.tobynguyen27.sense.util.TagUtils.createTag<Block>(AstralGenerators.MOD_ID, name)
}
