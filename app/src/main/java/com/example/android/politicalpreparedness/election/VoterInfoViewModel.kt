package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.network.CivicsApi
import com.example.android.politicalpreparedness.data.network.models.Division
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    private val _followElection = MutableLiveData<Boolean>()
    val followElection: LiveData<Boolean>
        get() = _followElection

    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    fun getVoterInfo(division: Division, electionId: String) {
        viewModelScope.launch {
            _followElection.value = dataSource.getElectionById(electionId) != null

            val address = if (division.state.isNotEmpty()) {
                "${division.state},${division.country}"
            } else {
                division.country
            }

            try {
                _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(address, electionId)
            } catch(e: Exception) {
                Log.e("TAG", e.localizedMessage)
            }
        }
    }

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    fun saveElection() {
        _voterInfo.value?.election?.let { election ->
            viewModelScope.launch {
                if (dataSource.getElectionById(election.id) == null) {
                    dataSource.insertElection(election)
                    _followElection.value = false
                } else {
                    dataSource.deleteElectionById(election.id)
                    _followElection.value = true
                }
            }
        }
    }

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}