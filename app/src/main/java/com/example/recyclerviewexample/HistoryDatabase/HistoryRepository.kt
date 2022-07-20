package com.example.recyclerviewexample.HistoryDatabase

import java.text.FieldPosition

class HistoryRepository(private val dao: HistoryDetailDAO) {

    val history = dao.getAllHistory()

    suspend fun insert(history: HistoryDetail) {
        dao.insertHistory(history)
    }

    suspend fun delete(history: HistoryDetail) {
        dao.deleteHistory(history)
    }

    fun getHistoryDetail(date: String ): HistoryDetail {
        return dao.getHistoryDetail(date)
    }

    suspend fun getListHistory(): List<HistoryDetail> {
        return dao.getListHistory()
    }
}