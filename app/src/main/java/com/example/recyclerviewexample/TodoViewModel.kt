package com.example.recyclerviewexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.recyclerviewexample.TodoDatabase.TodoDatabase
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import com.example.recyclerviewexample.TodoDatabase.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {
    val allTodoDetail: LiveData<List<TodoDetail>>
    val repository: TodoRepository

    init {
        val dao = TodoDatabase.getInstance(application).todoDetailDAO()
        repository = TodoRepository(dao)
        allTodoDetail = repository.todos
    }

    fun insertTodo(todoDetail: TodoDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(todoDetail)
    }

    fun updateTodo(todoDetail: TodoDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.update(todoDetail)
    }

    fun deleteTodo(todoDetail: TodoDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.delete(todoDetail )
    }

    suspend fun getTodoDetail(itemName: String): TodoDetail {
        return repository.getTodoDetail(itemName)
    }

    suspend fun getListTodos(): List<TodoDetail> {
        return repository.getListTodos()
    }
}