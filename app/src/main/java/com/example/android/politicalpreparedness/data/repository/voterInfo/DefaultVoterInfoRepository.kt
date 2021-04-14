package com.example.android.politicalpreparedness.data.repository.voterInfo

import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultVoterInfoRepository(
        private val remoteDataSource: VoterInfoDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): VoterInfoRepository {
    override suspend fun getVoterInfo(address: String, electionId: String): Result<VoterInfoResponse> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(remoteDataSource.getVoterInfo(address, electionId))
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }
}