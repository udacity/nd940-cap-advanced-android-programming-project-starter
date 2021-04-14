package com.example.android.politicalpreparedness.data.repository.election

import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.network.models.Election

class ElectionLocalDataSource(
        private val electionDao: ElectionDao
): ElectionDataSource {
    override suspend fun insertElection(election: Election) {
        electionDao.insertElection(election)
    }

    override suspend fun getElections(): List<Election> {
        return electionDao.getElections()
    }

    override suspend fun getElectionById(id: String): Election? {
        return electionDao.getElectionById(id)
    }

    override suspend fun deleteElectionById(id: String) {
        electionDao.deleteElectionById(id)
    }

    override suspend fun deleteCompleteElections() {
        electionDao.deleteCompleteElections()
    }
}