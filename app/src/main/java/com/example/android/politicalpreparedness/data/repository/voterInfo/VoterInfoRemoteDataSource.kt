package com.example.android.politicalpreparedness.data.repository.voterInfo

import com.example.android.politicalpreparedness.data.network.CivicsApi
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse

class VoterInfoRemoteDataSource: VoterInfoDataSource {
    override suspend fun getVoterInfo(electionId: String, address: String): VoterInfoResponse {
        return CivicsApi.retrofitService.getVoterInfo(address, electionId)
    }
}