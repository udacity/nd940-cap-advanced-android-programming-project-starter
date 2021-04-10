package com.example.android.politicalpreparedness.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.data.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: Election)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
    suspend fun getElections(): List<Election>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id = :id")
    suspend fun getElectionById(id: String): Election?

    //TODO: Add delete query
    @Query("DELETE FROM election_table WHERE id = :id")
    suspend fun deleteElectionById(id: String): Int

    //TODO: Add clear query
    @Query("DELETE FROM election_table")
    suspend fun deleteCompleteElections(): Int

}