package com.example.recyclerviewexample.HistoryDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import com.example.recyclerviewexample.database.ExerciseDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "history_data_table")
data class HistoryDetail(

    var arrayList: ArrayList<TodoDetail>,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_id")
    var workoutId: Int,

    var date: String,

//  0 = Punch training, 1 = Weight training, 2 = Run training, 3 = Yoga training
    val type: Int = 0,

    val notes: String
) {}

class arrayListTypeConverter {
    @TypeConverter
    fun fromTodoDetail(value: String): ArrayList<TodoDetail> {

        val listType = object : TypeToken<ArrayList<TodoDetail>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<TodoDetail>): String {
     return Gson().toJson(list)
    }
}