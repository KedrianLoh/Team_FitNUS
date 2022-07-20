package com.example.recyclerviewexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail
import com.example.recyclerviewexample.HistoryDatabase.HistoryRepository
import com.example.recyclerviewexample.HistoryDatabase.WorkoutHistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application): AndroidViewModel(application) {
    val allHistoryDetail: LiveData<List<HistoryDetail>>
    val repository: HistoryRepository

    init {
        val dao = WorkoutHistoryDatabase.getInstance(application).historyDetailDAO()
        repository = HistoryRepository(dao)
        allHistoryDetail = repository.history
    }

    fun insertHistory(history: HistoryDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(history)
    }

    fun deleteHistory(history: HistoryDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.delete(history)
    }

    fun getHistoryDetail(date: String): HistoryDetail {
        return repository.getHistoryDetail(date)
    }

    suspend fun getListHistory(): List<HistoryDetail> {
       return repository.getListHistory()
    }
}