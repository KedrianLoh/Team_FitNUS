package com.example.recyclerviewexample.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseDatabaseTest : TestCase() {

    private lateinit var db: ExerciseDatabase
    private lateinit var dao: ExerciseDetailDAO

    @Before // called before each test function
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ExerciseDatabase::class.java)
            .build() // temporary database just for testing purposes
        dao = db.exerciseDetailDAO()
    }

    @After // called after each test function
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadExercise() = runBlocking {
        val exerciseDetail = ExerciseDetail("apple", "1", "1", "1")
        dao.insertExercise(exerciseDetail)
        val exercises = dao.getListExercise()
        assertThat(exercises.contains(exerciseDetail)).isTrue()
    }

    @Test
    fun deleteAndConfirm() = runBlocking {
        val exerciseDetail = ExerciseDetail("apple", "1", "1", "1")
        dao.insertExercise(exerciseDetail)
        var exercises = dao.getListExercise()
        assertThat(exercises.contains(exerciseDetail)).isTrue()
        dao.deleteExercise(exerciseDetail)
        exercises = dao.getListExercise()
        assertThat(exercises.isEmpty()).isTrue()
    }

    @Test
    fun getIdAndConfirm() = runBlocking {
        val exerciseDetail = ExerciseDetail("apple", "1", "1", "1")
        dao.insertExercise(exerciseDetail)
        val exercises = dao.getListExercise()[0]
        val test = dao.getId("apple")
        assertThat(exercises == test).isTrue()
    }


}