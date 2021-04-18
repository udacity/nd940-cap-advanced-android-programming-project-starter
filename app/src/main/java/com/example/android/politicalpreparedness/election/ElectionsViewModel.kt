package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.repository.election.ElectionRepository
import com.example.android.politicalpreparedness.utils.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

//TODO: Construct ViewModel and provide election datasource
@Suppress("UNCHECKED_CAST")
class ElectionsViewModel(
        private val repository: ElectionRepository
): BaseViewModel() {

    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election>
        get() = _navigateToVoterInfo

    //TODO: Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    //TODO: Create live data val for saved elections
    private val _savedUpcomingElections = MutableLiveData<List<Election>>()
    val savedUpcomingElections: LiveData<List<Election>>
        get() = _savedUpcomingElections

    //TODO: Populate recycler adapters
    init {
        getRemoteElections()
        showRefreshing.value = false
    }

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun getRemoteElections() {
        viewModelScope.launch {
            showLoading.value = true
            try {
                when(val refreshedListResponse = repository.getRefreshedElections()) {
                    is Result.Success<*> -> {
                        _upcomingElections.value = refreshedListResponse.data as List<Election>
                        showLoading.value = false
                        showRefreshing.value = false
                    }
                    is Result.Error -> {
                        showLoading.value = false
                        showRefreshing.value = false
                        showErrorMessageInt.value = R.string.error_occurs
                    }
                }
            } catch (e: Exception) {
                showLoading.value = false
                showRefreshing.value = false
                _upcomingElections.value = listOf()
                showErrorMessageInt.value = R.string.error_occurs
            }
        }
    }

    fun getSavedElections() {
        viewModelScope.launch {
            try {
                when(val savedListResponse = repository.getSavedElections()) {
                    is Result.Success<*> -> {
                        _savedUpcomingElections.value = savedListResponse.data as List<Election>
                    }
                    else -> {
                        _savedUpcomingElections.value = listOf()
                    }
                }
            } catch (e: Exception) {
                _savedUpcomingElections.value = listOf()
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun displayVoterInfo(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun displayVoterInfoComplete() {
        _navigateToVoterInfo.value = null
    }
}