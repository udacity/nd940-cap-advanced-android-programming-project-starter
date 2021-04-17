package com.example.android.politicalpreparedness.data.repository.representative

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.RepresentativeResponse

interface RepresentativeRepository {
    suspend fun getRepresentatives(address: String): Result<RepresentativeResponse>
}