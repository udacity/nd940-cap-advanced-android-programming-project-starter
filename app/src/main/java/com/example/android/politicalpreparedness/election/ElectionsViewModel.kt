package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.helper.ApiStatus
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.data.repository.ElectionRepository
import com.example.android.politicalpreparedness.data.succeeded
import kotlinx.coroutines.launch
import java.lang.Exception

//TODO: Construct ViewModel and provide election datasource
@Suppress("UNCHECKED_CAST")
class ElectionsViewModel(
        private val repository: ElectionRepository,
        applicationContext: Application
): AndroidViewModel(applicationContext) {

    private val _navigateToSelectedVoterInfo = MutableLiveData<VoterInfoResponse>()
    val navigateToSelectedVoterInfo: LiveData<VoterInfoResponse>
        get() = _navigateToSelectedVoterInfo

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    //TODO: Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    //TODO: Create live data val for saved elections
    private val _savedUpcomingElections = MutableLiveData<List<Election>>()
    val savedUpcomingElections: LiveData<List<Election>>
        get() = _savedUpcomingElections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun getSavedAndRemoteElections() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                when(val savedListResponse = repository.getSavedElections()) {
                    is Result.Success<*> -> {
                        _savedUpcomingElections.value = savedListResponse.data as List<Election>
                    }
                    is Result.Error -> {
                        _status.value = ApiStatus.ERROR
                    }
                }

                when(val refreshedListResponse = repository.getRefreshedElections()) {
                    is Result.Success<*> -> {
                        _upcomingElections.value = refreshedListResponse.data as List<Election>
                    }
                    is Result.Error -> {
                        _status.value = ApiStatus.ERROR
                    }
                }

                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _savedUpcomingElections.value = listOf()
                _upcomingElections.value = listOf()
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun displayVoterInfo(voterInfo: VoterInfoResponse) {
        _navigateToSelectedVoterInfo.value = voterInfo
    }

    fun displayVoterInfoComplete() {
        _navigateToSelectedVoterInfo.value = null
    }
}