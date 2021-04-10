package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.data.network.models.Election

interface ElectionRepository {
    suspend fun saveElection(election: Election)

    suspend fun getRefreshedElections(): List<Election>

    suspend fun getSavedElections(): List<Election>

    suspend fun getElectionById(id: String): Election?

    suspend fun deleteElectionById(id: String): Int

    suspend fun deleteCompleteElections(): Int
}