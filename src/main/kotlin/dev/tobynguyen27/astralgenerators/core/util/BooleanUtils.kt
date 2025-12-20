package dev.tobynguyen27.astralgenerators.core.util

object BooleanUtils {

    fun toInt(bool: Boolean): Int {
        return if (bool) {
            0
        } else {
            1
        }
    }

    fun fromIntToBool(int: Int): Boolean {
        return int == 0
    }
}
