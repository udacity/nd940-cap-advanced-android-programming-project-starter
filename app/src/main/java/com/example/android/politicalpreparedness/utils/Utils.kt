package com.example.android.politicalpreparedness.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return format.format(this)
}