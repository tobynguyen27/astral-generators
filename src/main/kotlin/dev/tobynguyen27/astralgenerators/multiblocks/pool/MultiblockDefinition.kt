package dev.tobynguyen27.astralgenerators.multiblocks.pool

data class MultiblockDefinition(
    val origin: Origin,
    val keys: HashMap<Char, Block>,
    val pattern: List<List<String>>,
)

data class Block(val block: String, val nbt: String?)

data class Origin(val x: Int, val y: Int, val z: Int)
