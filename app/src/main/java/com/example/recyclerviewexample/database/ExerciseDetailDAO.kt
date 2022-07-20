package com.example.recyclerviewexample.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface ExerciseDetailDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(Exercise: ExerciseDetail)

    @Delete
    suspend fun deleteExercise(Exercise: ExerciseDetail)

   /* @Update
    suspend fun updateExercise(Exercise: ExerciseDetail): Int

    @Delete
    suspend fun deleteExercise(Exercise: ExerciseDetail): Int

    @Query("DELETE FROM exercise_data_table")
    suspend fun deleteAll(): Int */

    @Query("SELECT * FROM exercise_data_table")
    fun getAllExercises(): LiveData<List<ExerciseDetail>>

    @Query("SELECT * FROM exercise_data_table WHERE exercise_name LIKE :searchQuery OR body_part LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ExerciseDetail>>

//    @Query("SELECT exercise_name FROM exercise_data_table WHERE exercise_id LIKE :position")
//    suspend fun getName(position: Int): String

    @Query("SELECT * FROM exercise_data_table WHERE exercise_name LIKE :name")
    suspend fun getId(name: String): ExerciseDetail

    @Query("SELECT * FROM exercise_data_table")
    suspend fun getListExercise(): List<ExerciseDetail>

}