package dev.tobynguyen27.astralgenerators.contents.lang

import dev.tobynguyen27.astralgenerators.AstralGenerators

object Texts {
    const val ENERGY = "text.energy"
    const val CAPACITY = "text.capacity"
    const val STORED = "text.stored"

    fun register() {
        val texts: Map<String, String> =
            mapOf(ENERGY to "Energy", CAPACITY to "Capacity", STORED to "Stored")

        texts.forEach { (k, v) -> AstralGenerators.REGISTRATE.addRawLang(k, v) }
    }
}
