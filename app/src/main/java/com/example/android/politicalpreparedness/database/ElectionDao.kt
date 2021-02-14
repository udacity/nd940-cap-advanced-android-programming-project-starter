package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: Election)

    @Query("select * from election_table order by electionDay asc")
    suspend fun getElections(): LiveData<List<Election>>

    @Query("select * from election_table where id = :electionId")
    suspend fun getElection(electionId: Int)

    @Query("delete from election_table where id = :electionId")
    suspend fun deleteElection(electionId: Int)

    @Query("delete from election_table")
    suspend fun deleteAllElections()
}