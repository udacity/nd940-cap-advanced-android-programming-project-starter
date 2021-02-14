package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

class ElectionsViewModel(application: Application): AndroidViewModel(application) {

    private val database = getInstance(application)
    private val electionRepository = ElectionRepository(database)

    val upcomingElections = electionRepository.upcomingElections
    val savedElections = electionRepository.savedElections

    private val _errorOnFetchingNetworkData = MutableLiveData<Boolean>(false)
    val errorOnFetchingNetworkData: LiveData<Boolean>
        get() = _errorOnFetchingNetworkData

    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election>
        get() =_navigateToVoterInfo

    fun displayNetworkErrorCompleted() {
        _errorOnFetchingNetworkData.value = false
    }

    fun onElectionClicked(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun navigationToVoterInfoComplete() {
        _navigateToVoterInfo.value = null
    }

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    init {
        refreshUpcomingElections()
    }

    private fun refreshUpcomingElections() {
        viewModelScope.launch {
            try {
                electionRepository.refreshUpcomingElections()
                _errorOnFetchingNetworkData.value = false
            } catch (networkError: IOException) {
                if(upcomingElections.value.isNullOrEmpty()) {
                    _errorOnFetchingNetworkData.value = true
                }
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}