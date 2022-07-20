package com.example.recyclerviewexample

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.recyclerviewexample.database.ExerciseDatabase
import com.example.recyclerviewexample.database.ExerciseDetail
import com.example.recyclerviewexample.database.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// it is the moderator between the UI and data
class ExerciseViewModel(application: Application): AndroidViewModel(application) {
    val allExerciseDetail: LiveData<List<ExerciseDetail>>
    val repository: ExerciseRepository

    // initialize the above data

    init {
        val dao = ExerciseDatabase.getInstance(application).exerciseDetailDAO()
        repository = ExerciseRepository(dao)
        allExerciseDetail = repository.exercises
    }

    // insert exercise function
    // we want function to run in the background thread
    // the dispatchers.IO are in kotlin coroutines
    // it means that the task will be performed in the IO thread as background thread
    // if we want the task in the main thread, we will use Dispatchers.Main
    // but we are performing the task in the background
    fun insertExercise(exercise: ExerciseDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(exercise)
    }

    fun deleteExercise(exercise: ExerciseDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.delete(exercise)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ExerciseDetail>> {
        return repository.searchDatabase(searchQuery)
    }

//    suspend fun getName(position: Int): String {
//        return repository.getName(position)
//    }

    suspend fun getId(name: String): ExerciseDetail {
        return repository.getId(name)
    }

     suspend fun getListExercise(): List<ExerciseDetail> {
        return repository.getListExercises()
    }

}