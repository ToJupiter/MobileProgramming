import kotlin.math.abs

class Fraction(var numerator: Int, var denominator: Int) {
    init {
        require(denominator != 0) { "Denominator cannot be zero!" }
    }

    fun input() {
        do {
            print("Enter numerator: ")
            val num = readln().toInt()
            print("Enter denominator (≠ 0): ")
            val den = readln().toInt()
            if (den != 0) {
                numerator = num
                denominator = den
                return
            }
            println("Denominator must not be zero. Please re-enter!")
        } while (true)
    }

    fun print() = println("$numerator/$denominator")

    fun simplify(): Fraction {
        val g = gcd(abs(numerator), abs(denominator))
        return Fraction(numerator / g, denominator / g)
    }

    fun compare(other: Fraction): Int {
        val lhs = numerator * other.denominator
        val rhs = other.numerator * denominator
        return when {
            lhs < rhs -> -1
            lhs == rhs -> 0
            else -> 1
        }
    }

    fun add(other: Fraction): Fraction {
        val newNumerator = numerator * other.denominator + other.numerator * denominator
        val newDenominator = denominator * other.denominator
        return Fraction(newNumerator, newDenominator).simplify()
    }

    private fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}

fun main() {
    print("Enter number of fractions: ")
    val n = readln().toInt()
    val arr = mutableListOf<Fraction>()

    // Input array of fractions
    repeat(n) {
        println("Fraction ${it + 1}:")
        val f = Fraction(0, 1)
        f.input()
        arr.add(f)
    }

    // Print original array
    println("\nArray of fractions entered:")
    arr.forEach { it.print() }

    // Simplify and print
    println("\nAfter simplification:")
    arr.map { it.simplify() }.forEach { it.print() }

    // Calculate sum
    val sum = arr.reduce { acc, frac -> acc.add(frac) }.simplify()
    println("\nSum of all fractions: ${sum.numerator}/${sum.denominator}")

    // Find maximum fraction
    var max = arr[0]
    for (f in arr) {
        if (f.compare(max) == 1) max = f
    }
    val simplifiedMax = max.simplify()
    println("\nMaximum fraction: ${simplifiedMax.numerator}/${simplifiedMax.denominator}")

    // Sort in descending order
    val sorted = arr.sortedWith { a, b -> b.compare(a) }
    println("\nArray sorted in descending order:")
    sorted.map { it.simplify() }.forEach { it.print() }
}