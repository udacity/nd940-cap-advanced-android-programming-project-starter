package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.Repository
import kotlinx.coroutines.launch
import java.io.IOException

class ElectionsViewModel(private val repository: Repository): ViewModel() {

    val upcomingElections = repository.upcomingElections
    val savedElections = repository.savedElections

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

    init {
        refreshUpcomingElections()
    }

    private fun refreshUpcomingElections() {
        viewModelScope.launch {
            try {
                repository.refreshUpcomingElections()
                _errorOnFetchingNetworkData.value = false
            } catch (networkError: IOException) {
                if(upcomingElections.value.isNullOrEmpty()) {
                    _errorOnFetchingNetworkData.value = true
                }
            }
        }
    }
}