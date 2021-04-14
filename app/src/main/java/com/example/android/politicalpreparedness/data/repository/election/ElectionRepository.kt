package com.example.android.politicalpreparedness.data.repository.election

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.Election

interface ElectionRepository {
    suspend fun saveElection(election: Election)

    suspend fun getRefreshedElections(): Result<List<Election>>

    suspend fun getSavedElections(): Result<List<Election>>

    suspend fun getElectionById(id: String): Result<Election>

    suspend fun deleteElectionById(id: String)

    suspend fun deleteCompleteElections()
}