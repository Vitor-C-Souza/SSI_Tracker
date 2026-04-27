package com.ssitracker.app.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    /**
     * Formata uma data no formato "Sexta-feira, Abril 24" (dependendo do Locale).
     * Padrão: "EEEE, MMMM d"
     */
    fun formatToFullDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH)
        return date.format(formatter)
    }

    /**
     * Formata uma data no formato "Monday, April 27, 2026".
     * Padrão: "EEEE, MMMM d, yyyy"
     */
    fun formatToFullDateWithYear(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }
}
