package com.example.recyclerviewexample.ProfileDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDetailDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProfile(Profile: ProfileDetail)

    @Query("SELECT * FROM profile_data_table")
    fun getAllProfiles(): LiveData<List<ProfileDetail>>

    @Query("SELECT * FROM profile_data_table")
    fun getListProfile(): List<ProfileDetail>
}