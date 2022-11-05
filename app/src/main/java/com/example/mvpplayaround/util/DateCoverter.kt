package com.example.mvpplayaround.util

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun formatDateToDdMmYyyy(date: String): String {
        val ddMmYyyyFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val yyyyMMddFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val yyyyMMddDate = yyyyMMddFormat.parse(date)

            yyyyMMddDate?.let {
                return ddMmYyyyFormat.format(yyyyMMddDate)
            }
        } catch (e: Exception) {

        }


        return ""
    }
}