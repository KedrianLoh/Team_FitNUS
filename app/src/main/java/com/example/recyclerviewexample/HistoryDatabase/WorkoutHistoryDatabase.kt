package com.example.recyclerviewexample.HistoryDatabase

import android.content.Context
import androidx.room.*

@Database(entities = [HistoryDetail::class], version = 1, exportSchema = false)
@TypeConverters(arrayListTypeConverter::class)
abstract class WorkoutHistoryDatabase: RoomDatabase() {

    abstract fun historyDetailDAO(): HistoryDetailDAO

    companion object {
        @Volatile
        private var INSTANCE: WorkoutHistoryDatabase? = null
        fun getInstance(context: Context): WorkoutHistoryDatabase {
            synchronized(this) {
                var instance: WorkoutHistoryDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutHistoryDatabase::class.java,
                        "history_database"
                    ).build()
                }
                return instance
            }
        }
    }
}