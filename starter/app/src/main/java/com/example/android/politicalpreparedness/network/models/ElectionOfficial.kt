package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json

data class ElectionOfficial(
    val name: String,
    val title: String,
    @Json(name="officePhoneNumber") val phone: String,
    @Json(name="faxNumber") val fax: String,
    val emailAddress: String
)