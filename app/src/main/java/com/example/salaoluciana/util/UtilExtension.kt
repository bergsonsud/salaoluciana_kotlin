package com.example.salaoluciana.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun Float.formataParaBr() : String {
    val moeda = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))
    return moeda.format(this.toBigDecimal()).replace(moeda.currency.currencyCode, moeda.currency.currencyCode + " ")
}


fun String.capitalizeWords(): String = split(" ").map { it.toLowerCase().capitalize() }.joinToString(" ")


