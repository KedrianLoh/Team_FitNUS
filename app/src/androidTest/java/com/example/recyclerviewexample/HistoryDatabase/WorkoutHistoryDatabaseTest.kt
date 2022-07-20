package com.example.recyclerviewexample.HistoryDatabase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import com.example.recyclerviewexample.database.ExerciseDatabase
import com.example.recyclerviewexample.database.ExerciseDetail
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WorkoutHistoryDatabaseTest : TestCase() {

    private lateinit var db :WorkoutHistoryDatabase
    private lateinit var dao : HistoryDetailDAO

    @Before // called before each test function
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WorkoutHistoryDatabase::class.java)
            .build() // temporary database just for testing purposes
        dao = db.historyDetailDAO()
    }

    @After // called after each test function
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadHistory() = runBlocking {
        val todoDetail = TodoDetail("apple", "1","1", "1", "1", 0)
        val list = arrayListOf<TodoDetail>()
        list.add(todoDetail)
        val historyDetail = HistoryDetail(list, 0, "orange")

        dao.insertHistory(historyDetail)
        val history = dao.getListHistory()
        assertThat(history.contains(historyDetail)).isTrue()
    }

//    @Test
//    fun deleteAndConfirm() = runBlocking {
//        val exerciseDetail = ExerciseDetail("apple", "1", "1", "1")
//        dao.insertExercise(exerciseDetail)
//        var exercises = dao.getListExercise()
//        Truth.assertThat(exercises.contains(exerciseDetail)).isTrue()
//        dao.deleteExercise(exerciseDetail)
//        exercises = dao.getListExercise()
//        assertThat(exercises.isEmpty()).isTrue()
//    }
//
//    @Test
//    fun getIdAndConfirm() = runBlocking {
//        val exerciseDetail = ExerciseDetail("apple", "1", "1", "1")
//        dao.insertExercise(exerciseDetail)
//        val exercises = dao.getListExercise()[0]
//        val test = dao.getId("apple")
//        assertThat(exercises == test).isTrue()
//    }
//
}