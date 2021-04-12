package com.example.android.politicalpreparedness.data.network.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

private const val PATTERN = "yyyy-MM-dd"

class DateAdapter {
    @ToJson
    fun dateToJson(value: Date): String {
        val format = SimpleDateFormat(PATTERN, Locale.getDefault())
        return format.format(value)
    }

    @FromJson
    fun dateFromJson(value: String): Date {
        val format = SimpleDateFormat(PATTERN, Locale.getDefault())
        return format.parse(value)!!
    }
}