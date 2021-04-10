package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.data.network.models.Election
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultElectionRepository(
        private val electionRemoteDataSource: ElectionDataSource,
        private val electionLocalDataSource: ElectionDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ElectionRepository {
    override suspend fun saveElection(election: Election) {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshedElections(): List<Election> {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedElections(): List<Election> {
        TODO("Not yet implemented")
    }

    override suspend fun getElectionById(id: String): Election? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteElectionById(id: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompleteElections(): Int {
        TODO("Not yet implemented")
    }
}