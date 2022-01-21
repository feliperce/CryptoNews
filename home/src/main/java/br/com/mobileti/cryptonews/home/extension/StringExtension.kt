package br.com.mobileti.cryptonews.home.extension

import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toFormattedDateString(oldFormat: String, newFormat: String): String {
    return try {
        SimpleDateFormat(oldFormat, Locale.getDefault()).parse(this)?.let {
            SimpleDateFormat(newFormat, Locale.getDefault()).format(it)
        } ?: ""
    } catch (e: Exception) {
        return ""
    }
}