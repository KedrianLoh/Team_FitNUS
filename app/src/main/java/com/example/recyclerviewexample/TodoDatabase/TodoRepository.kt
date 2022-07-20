package com.example.recyclerviewexample.TodoDatabase

import androidx.sqlite.db.SupportSQLiteQuery

class TodoRepository(private val dao: TodoDetailDAO) {

    val todos = dao.getAllTodos()

    suspend fun insert(todoDetail: TodoDetail) {
        dao.insertTodo(todoDetail)
    }

    suspend fun update(todoDetail: TodoDetail) {
        dao.updateTodo(todoDetail)
    }

    suspend fun delete(todoDetail: TodoDetail) {
        dao.deleteTodo(todoDetail)
    }

    suspend fun getTodoDetail(itemName: String): TodoDetail {
        return dao.getTodoDetail(itemName)
    }

    suspend fun getListTodos(): List<TodoDetail> {
        return dao.getListTodos()
    }
}