package com.example.android.politicalpreparedness.data.repository.voterInfo

import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse

interface VoterInfoDataSource {
    suspend fun getVoterInfo(electionId: String, address: String): VoterInfoResponse
}