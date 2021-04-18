package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.Division
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.data.network.models.getBallotInfoUrl
import com.example.android.politicalpreparedness.data.network.models.getVotingLocationFinderUrl
import com.example.android.politicalpreparedness.data.repository.election.ElectionRepository
import com.example.android.politicalpreparedness.data.repository.voterInfo.VoterInfoRepository
import com.example.android.politicalpreparedness.utils.BaseViewModel
import kotlinx.coroutines.launch

class VoterInfoViewModel(
        private val voterInfoRepository: VoterInfoRepository,
        private val electionRepository: ElectionRepository

) : BaseViewModel() {
    private val _urlString = MutableLiveData<String>()
    val urlString: LiveData<String>
        get() = _urlString

    private val _followElection = MutableLiveData<Boolean>()
    val followElection: LiveData<Boolean>
        get() = _followElection

    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _hasVoterInfo = MutableLiveData<Boolean>()
    val hasVoterInfo: LiveData<Boolean>
        get() = _hasVoterInfo

    init {
        _hasVoterInfo.value = false
    }

    //TODO: Add var and methods to populate voter info
    fun getVoterInfo(division: Division, electionId: String) {
        viewModelScope.launch {

            showLoading.value = true

            _followElection.value = electionRepository.getElectionById(electionId) is Result.Error

            val address = if (division.state.isNotEmpty()) {
                "${division.state},${division.country}"
            } else {
                division.country
            }

            when(val result = voterInfoRepository.getVoterInfo(address, electionId.toInt())) {
                is Result.Success<*> -> {
                    _voterInfo.value = result.data as VoterInfoResponse
                    showLoading.value = false
                    _hasVoterInfo.value = true
                }
                is Result.Error -> {
                    showLoading.value = false
                    showErrorMessageInt.value = R.string.voter_info_error
                    _hasVoterInfo.value = false
                }
            }
        }
    }

    //TODO: Add var and methods to support loading URLs
    fun loadVotingLocation() {
        _urlString.value = _voterInfo.value?.firstState?.getVotingLocationFinderUrl()
    }

    fun loadBallotInformation() {
        _urlString.value = _voterInfo.value?.firstState?.getBallotInfoUrl()
    }

    fun loadUrlComplete() {
        _urlString.value = null
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status
    fun saveElection() {
        _voterInfo.value?.election?.let { election ->
            viewModelScope.launch {
                if (followElection.value == true) {
                    electionRepository.saveElection(election)
                    _followElection.value = false
                } else {
                    electionRepository.deleteElectionById(election.id)
                    _followElection.value = true
                }
            }
        }
    }
    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */
}