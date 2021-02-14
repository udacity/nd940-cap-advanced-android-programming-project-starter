package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionRepository(database: ElectionDatabase) {

    val upcomingElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>> = database.electionDao.getElections()

    suspend fun refreshUpcomingElections() {
        withContext(Dispatchers.IO) {
            val electionResponse = CivicsApi.retrofitService.getElections()
            upcomingElections.postValue(electionResponse.elections)
        }
    }

}