package com.example.recyclerviewexample.database

import androidx.lifecycle.LiveData
import androidx.room.Query

// it will take dao as constructor
class ExerciseRepository(private val dao: ExerciseDetailDAO) {

    val exercises = dao.getAllExercises() // all exercise details

    // define the insert function here
    suspend fun insert(exercise: ExerciseDetail) {
        dao.insertExercise(exercise)
    }

    suspend fun delete(exercise: ExerciseDetail) {
        dao.deleteExercise(exercise)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ExerciseDetail>> {
        return dao.searchDatabase(searchQuery)
    }

//    suspend fun getName(position: Int): String {
//        return dao.getName(position)
//    }

    suspend fun getId(name: String): ExerciseDetail {
        return dao.getId(name)
    }

    suspend fun getListExercises(): List<ExerciseDetail> {
        return dao.getListExercise()
    }

    /* suspend fun update(exercise: ExerciseDetail): Int {
        return dao.updateExercise(exercise)
    }

    suspend fun delete(exercise: ExerciseDetail): Int {
        return dao.deleteExercise(exercise)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    } */
}