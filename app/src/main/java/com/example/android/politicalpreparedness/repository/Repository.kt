package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class Repository(private val database: ElectionDatabase) {

    val upcomingElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>> = database.electionDao.getElections()
    val state = MutableLiveData<State>()

    suspend fun refreshUpcomingElections() {
        withContext(Dispatchers.IO) {
            try {
                val electionResponse = CivicsApi.retrofitService.getElections()
                upcomingElections.postValue(electionResponse.elections)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    suspend fun refreshVoterInfo(address: String, electionId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val voterResponse = CivicsApi.retrofitService.getVoterInfo(address, electionId)
                state.postValue(voterResponse.state?.get(0))
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    suspend fun followElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.insertElection(election)
        }
    }

    suspend fun unfollowElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.deleteElection(election.id)
        }
    }

    suspend fun isElectionSaved(election: Election): Boolean {
        val e: Election
        withContext(Dispatchers.IO) {
            e = database.electionDao.getElection(election.id)
        }
        return e == election
    }
}