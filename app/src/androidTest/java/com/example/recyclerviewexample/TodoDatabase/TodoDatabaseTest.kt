package com.example.recyclerviewexample.TodoDatabase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recyclerviewexample.database.ExerciseDatabase
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoDatabaseTest : TestCase() {

    private lateinit var db: TodoDatabase
    private lateinit var dao: TodoDetailDAO

    @Before // called before each test function
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java)
            .build() // temporary database just for testing purposes
        dao = db.todoDetailDAO()
    }

    @After // called after each test function
    fun closeDb() {
        db.close()
    }

    fun insertTodo() = runBlocking {
        val todoDetail = TodoDetail("apple", "1", "1", "1", "1", 0)
        dao.insertTodo(todoDetail)
    }

    @Test
    fun insertAndReadTodo() {
        insertTodo()
        val todoDetail = TodoDetail("apple", "1", "1", "1", "1", 0)
        val todos = dao.getListTodos()
        assertThat(todos.contains(todoDetail)).isTrue()
    }
}