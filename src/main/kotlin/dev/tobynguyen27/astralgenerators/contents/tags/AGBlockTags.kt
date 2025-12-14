package dev.tobynguyen27.astralgenerators.contents.tags

import dev.tobynguyen27.astralgenerators.core.util.TagUtils
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object AGBlockTags {
    val CASING: TagKey<Block> = TagUtils.create("casings")

    val COIL = TagUtils.create("coils")
}
