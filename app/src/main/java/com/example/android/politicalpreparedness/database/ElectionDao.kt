package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElection(election: Election)

    @Query("select * from election_table order by electionDay asc")
    fun getElections(): LiveData<List<Election>>

    @Query("select * from election_table where id = :electionId")
    fun getElection(electionId: Int): Election

    @Query("delete from election_table where id = :electionId")
    fun deleteElection(electionId: Int)

    @Query("delete from election_table")
    fun deleteAllElections()
}