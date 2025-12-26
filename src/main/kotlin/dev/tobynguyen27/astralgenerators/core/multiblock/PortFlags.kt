package dev.tobynguyen27.astralgenerators.core.multiblock

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType

class PortFlags(val flags: Int) {
    fun allows(type: PortBlockType): Boolean {
        return (flags and (1 shl type.value)) > 0
    }

    class Builder {

        var flags = 0

        fun with(type: PortBlockType): Builder {
            flags = flags or (1 shl type.value)
            return this
        }

        fun with(vararg types: PortBlockType): Builder {
            types.forEach { with(it) }

            return this
        }

        fun build(): PortFlags {
            return PortFlags(flags)
        }
    }
}
