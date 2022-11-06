package com.example.mvpplayaround.util

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun formatDateToDdMmYyyy(date: String): String {
        if (validateddMMyyyyFormat(date)) {
            val ddMmYyyyFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val yyyyMMddFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            try {
                val yyyyMMddDate = yyyyMMddFormat.parse(date)

                yyyyMMddDate?.let {
                    return ddMmYyyyFormat.format(yyyyMMddDate)
                }
            } catch (e: Exception) {

            }
        }

        return ""
    }

    fun validateddMMyyyyFormat(date: String): Boolean {
        val regex = Regex("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
        return regex.containsMatchIn(date)
    }
}