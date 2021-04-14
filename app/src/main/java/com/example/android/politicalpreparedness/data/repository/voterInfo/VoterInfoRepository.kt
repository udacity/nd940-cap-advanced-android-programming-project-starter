package com.example.android.politicalpreparedness.data.repository.voterInfo

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse

interface VoterInfoRepository {
    suspend fun getVoterInfo(address: String, electionId: Int): Result<VoterInfoResponse>
}