package com.example.android.politicalpreparedness.data.repository.election

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.Election
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultElectionRepository(
        private val electionRemoteDataSource: ElectionDataSource,
        private val electionLocalDataSource: ElectionDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ElectionRepository {
    override suspend fun saveElection(election: Election) = withContext(ioDispatcher) {
        electionLocalDataSource.insertElection(election)
    }

    override suspend fun getRefreshedElections(): Result<List<Election>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(electionRemoteDataSource.getElections())
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun getSavedElections(): Result<List<Election>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(electionLocalDataSource.getElections())
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun getElectionById(id: String): Result<Election> = withContext(ioDispatcher) {
        return@withContext try {
            val election = electionLocalDataSource.getElectionById(id)

            if (election == null) {
                Result.Error("Election not found!")
            } else {
                Result.Success(election)
            }
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun deleteElectionById(id: String) = withContext(ioDispatcher) {
        electionLocalDataSource.deleteElectionById(id)
    }

    override suspend fun deleteCompleteElections() = withContext(ioDispatcher) {
        electionLocalDataSource.deleteCompleteElections()
    }
}