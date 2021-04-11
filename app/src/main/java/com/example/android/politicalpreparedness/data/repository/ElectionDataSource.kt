package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.Election

interface ElectionDataSource {
    suspend fun insertElection(election: Election)

    suspend fun getElections(): List<Election>

    suspend fun getElectionById(id: String): Election?

    suspend fun deleteElectionById(id: String)

    suspend fun deleteCompleteElections()
}