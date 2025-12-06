package dev.tobynguyen27.astralgenerators.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale.getDefault

object FormattingUtil {

    private val NUMBER_FORMAT = NumberFormat.getNumberInstance(getDefault())

    private val SUPERSCRIPTS = charArrayOf('⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹')
    private val SUBSCRIPTS = charArrayOf('₀', '₁', '₂', '₃', '₄', '₅', '₆', '₇', '₈', '₉')

    fun toSuperscript(str: String): String {
        return str.map { char -> if (char.isDigit()) SUPERSCRIPTS[char.digitToInt()] else char }
            .joinToString("")
    }

    fun toSubscript(str: String): String {
        return str.map { char -> if (char.isDigit()) SUBSCRIPTS[char.digitToInt()] else char }
            .joinToString("")
    }

    fun lowerUnderscoreToUpperCamel(str: String): String {
        return str.split('_').joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
    }

    fun toEnglishName(str: String): String =
        str.lowercase(getDefault()).split("_").joinToString(" ") {
            it.replaceFirstChar { c -> c.uppercaseChar() }
        }

    fun formatNumbers(number: Number): String {
        return NUMBER_FORMAT.format(number)
    }

    fun toReadableNumberString(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number)
    }

    fun calculateFormattedPercentage(a: Number, b: Number, pattern: String = "0.00"): String {
        if (b == 0.0) return "NaN"

        val percentage = (a.toDouble() / b.toDouble()) * 100
        val format = DecimalFormat(pattern)

        return format.format(percentage)
    }
}
