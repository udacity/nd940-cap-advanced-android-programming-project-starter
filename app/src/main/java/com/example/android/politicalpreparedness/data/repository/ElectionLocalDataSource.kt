package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.network.models.Election

class ElectionLocalDataSource(
        private val electionDao: ElectionDao
): ElectionDataSource {
    override suspend fun insertElection(election: Election) {
        TODO("Not yet implemented")
    }

    override suspend fun getElections(): List<Election> {
        TODO("Not yet implemented")
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