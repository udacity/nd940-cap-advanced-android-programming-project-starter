package com.example.android.politicalpreparedness.data.repository.representative

import com.example.android.politicalpreparedness.data.network.CivicsApi
import com.example.android.politicalpreparedness.data.network.models.RepresentativeResponse

class RepresentativeRemoteDataSource: RepresentativeDataSource {
    override suspend fun getRepresentatives(address: String): RepresentativeResponse {
        return CivicsApi.retrofitService.getRepresentatives(address)
    }
}