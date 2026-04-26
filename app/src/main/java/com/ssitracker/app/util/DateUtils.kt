package com.ssitracker.app.util

import android.os.Build
import androidx.annotation.RequiresApi
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
}
