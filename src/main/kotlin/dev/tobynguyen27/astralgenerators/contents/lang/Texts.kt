package dev.tobynguyen27.astralgenerators.contents.lang

import dev.tobynguyen27.astralgenerators.AstralGenerators

object Texts {
    const val ENERGY = "text.energy"
    const val CAPACITY = "text.capacity"
    const val STORED = "text.stored"
    const val PROGRESS = "text.progress"
    const val IDLING = "text.idling"

    fun register() {
        val texts: Map<String, String> =
            mapOf(
                ENERGY to "Energy",
                CAPACITY to "Capacity",
                STORED to "Stored",
                PROGRESS to "Progress",
                IDLING to "Idling",
            )

        texts.forEach { (k, v) -> AstralGenerators.REGISTRATE.addRawLang(k, v) }
    }
}
