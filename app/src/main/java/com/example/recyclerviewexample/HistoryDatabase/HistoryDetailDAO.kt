package com.example.recyclerviewexample.HistoryDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDetailDAO {
    @Insert
    suspend fun insertHistory(history: HistoryDetail)

    @Delete
    suspend fun deleteHistory(history:HistoryDetail)

    @Query("SELECT * FROM history_data_table")
    fun getAllHistory(): LiveData<List<HistoryDetail>>

    @Query("SELECT * FROM history_data_table WHERE date LIKE :date")
    fun getHistoryDetail(date: String): HistoryDetail

    @Query("SELECT * FROM history_data_table")
     suspend fun getListHistory(): List<HistoryDetail>
}