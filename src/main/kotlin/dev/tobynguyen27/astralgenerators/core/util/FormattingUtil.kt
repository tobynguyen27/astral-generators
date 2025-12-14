package dev.tobynguyen27.astralgenerators.core.util

import com.google.common.math.LongMath.gcd
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale.getDefault

object FormattingUtil {

    private val NUMBER_FORMAT = NumberFormat.getNumberInstance(getDefault())

    private val SUPERSCRIPTS = charArrayOf('⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹')
    private val SUBSCRIPTS = charArrayOf('₀', '₁', '₂', '₃', '₄', '₅', '₆', '₇', '₈', '₉')

    /** Convert `a2` to `a²` */
    fun toSuperscript(str: String): String {
        return str.map { char -> if (char.isDigit()) SUPERSCRIPTS[char.digitToInt()] else char }
            .joinToString("")
    }

    /** Convert `a2` to `a₂` */
    fun toSubscript(str: String): String {
        return str.map { char -> if (char.isDigit()) SUBSCRIPTS[char.digitToInt()] else char }
            .joinToString("")
    }

    /** Convert `iron_ingot` to `IronIngot` */
    fun lowerUnderscoreToUpperCamel(str: String): String {
        return str.split('_').joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
    }

    /** Convert `iron_ingot` to `Iron Ingot` */
    fun toEnglishName(str: String): String =
        str.lowercase(getDefault()).split("_").joinToString(" ") {
            it.replaceFirstChar { c -> c.uppercaseChar() }
        }

    /** Convert `1000000` to `1,000,000` */
    fun formatNumbers(number: Number): String {
        return NUMBER_FORMAT.format(number)
    }

    fun formatPercent(a: Number, b: Number, format: String = "0.00"): String {
        if (b == 0) return "NaN"

        val percentage = (a.toDouble() / b.toDouble()) * 100
        val format = DecimalFormat(format)

        return "${format.format(percentage)}%"
    }

    fun formatBuckets(droplets: Long): String {
        val dropletsPerB = 81000L
        val dropletsPerMB = 81L

        if (droplets % dropletsPerB == 0L) {
            val bValue = droplets / dropletsPerB
            return "$bValue B"
        }

        val mbInteger = droplets / dropletsPerMB
        val remainder = droplets % dropletsPerMB

        if (remainder == 0L) {
            return "$mbInteger mB"
        } else {
            val commonDivisor = gcd(remainder, dropletsPerMB)

            val numerator = remainder / commonDivisor
            val denominator = dropletsPerMB / commonDivisor

            return "$mbInteger ${toSuperscript("$numerator")}/${toSubscript("$denominator")} mB"
        }
    }

    fun convertCelsiusToFahrenheit(celsius: Double): Double {
        return celsius * 1.8 + 32
    }

    fun convertFahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) / 1.8
    }

    fun formatTemperature(temp: Double, useFahrenheit: Boolean = false): String {
        return if (useFahrenheit) {
            "$temp °F"
        } else {
            "$temp ℃"
        }
    }
}
