package com.example.recyclerviewexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// Room database are abstract classes
// annotate the database
// we have created entity
@Database(entities = [ExerciseDetail::class], version = 1, exportSchema = true)
abstract class ExerciseDatabase : RoomDatabase() {

    // we will access our dao through database
    abstract fun exerciseDetailDAO(): ExerciseDetailDAO

    // we will make a single instance of room database so it prevents multiple opening of database at the same time
    companion object {
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null
        fun getInstance(context: Context): ExerciseDatabase {
            synchronized(this) {
                var instance: ExerciseDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "exercise_database"
                    ).createFromAsset("database/defaultExerciseTable.db").build()
                }
                return instance
            }
        }
    }
}