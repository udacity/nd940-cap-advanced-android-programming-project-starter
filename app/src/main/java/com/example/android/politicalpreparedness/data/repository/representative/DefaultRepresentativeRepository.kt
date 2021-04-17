package com.example.android.politicalpreparedness.data.repository.representative

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.RepresentativeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultRepresentativeRepository(
        private val remoteDataSource: RepresentativeRemoteDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): RepresentativeRepository {
    override suspend fun getRepresentatives(address: String): Result<RepresentativeResponse> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(remoteDataSource.getRepresentatives(address))
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }
}