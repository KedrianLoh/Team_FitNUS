package com.example.recyclerviewexample.TodoDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface TodoDetailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoDetail: TodoDetail)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todoDetail: TodoDetail)

    @Delete
    suspend fun deleteTodo(todoDetail: TodoDetail)

    @Query("SELECT * FROM todo_data_table")
    fun getAllTodos(): LiveData<List<TodoDetail>>

    @Query("SELECT * FROM todo_data_table WHERE todo_name LIKE :itemName")
    fun getTodoDetail(itemName: String): TodoDetail

    @Query("SELECT * FROM todo_data_table")
    fun getListTodos(): List<TodoDetail>
}