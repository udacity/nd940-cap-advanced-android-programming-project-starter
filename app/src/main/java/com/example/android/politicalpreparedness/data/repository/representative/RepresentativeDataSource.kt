package com.example.android.politicalpreparedness.data.repository.representative

import com.example.android.politicalpreparedness.data.network.models.RepresentativeResponse

interface RepresentativeDataSource {
    suspend fun getRepresentatives(address: String): RepresentativeResponse
}