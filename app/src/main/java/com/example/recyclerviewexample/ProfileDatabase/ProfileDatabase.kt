package com.example.recyclerviewexample.ProfileDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProfileDetail::class], version = 1, exportSchema = false)
abstract class ProfileDatabase: RoomDatabase() {

    abstract fun profileDetailDAO(): ProfileDetailDAO

    companion object {
        @Volatile
        private var INSTANCE: ProfileDatabase? = null
        fun getInstance(context: Context): ProfileDatabase {
            synchronized(this) {
                var instance: ProfileDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileDatabase::class.java,
                        "profile_database"
                    ).build()
                }
                return instance
            }
        }
    }
}