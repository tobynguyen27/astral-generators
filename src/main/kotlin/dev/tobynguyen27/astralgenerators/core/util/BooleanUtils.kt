package dev.tobynguyen27.astralgenerators.core.util

object BooleanUtils {

    fun toInt(bool: Boolean): Int = if (bool) 0 else 1

    fun Int.toBool(): Boolean = this == 0
}
