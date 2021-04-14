package com.example.android.politicalpreparedness.data.repository.election

import com.example.android.politicalpreparedness.data.network.CivicsApi
import com.example.android.politicalpreparedness.data.network.models.Election

class ElectionRemoteDataSource: ElectionDataSource {
    override suspend fun insertElection(election: Election) {
        TODO("Not yet implemented")
    }

    override suspend fun getElections(): List<Election> {
        return CivicsApi.retrofitService.getElections().elections
    }

    override suspend fun getElectionById(id: String): Election? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteElectionById(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompleteElections() {
        TODO("Not yet implemented")
    }
}