package dev.tobynguyen27.astralgenerators.core.multiblock

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType

@JvmInline
value class PortFlags(val flags: Int) {
    fun allows(type: PortBlockType): Boolean {
        return (flags and (1 shl type.value)) > 0
    }

    companion object {
        operator fun invoke(vararg types: PortBlockType): PortFlags {
            return PortFlags(types.fold(0) { acc, type -> acc or (1 shl type.value) })
        }
    }

    class Builder {

        private var currentFlags = 0

        fun add(type: PortBlockType) {
            currentFlags = currentFlags or (1 shl type.value)
        }

        fun build(): PortFlags {
            return PortFlags(currentFlags)
        }
    }
}
