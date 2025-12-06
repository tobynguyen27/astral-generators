package dev.tobynguyen27.astralgenerators.contents.ports

import java.util.Locale.getDefault

object PortBlockSpecification {
    enum class Mode {
        INPUT,
        OUTPUT;

        override fun toString(): String {
            return super.toString().lowercase(getDefault())
        }
    }

    enum class Tier {
        BASIC,
        ADVANCED,
        INDUSTRIAL;

        override fun toString(): String {
            return super.toString().lowercase(getDefault())
        }
    }
}
