package com.example.recyclerviewexample.TodoDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_data_table")
class TodoDetail(

    @ColumnInfo(name = "todo_name")
    val name: String,

    @ColumnInfo(name = "todo_sets")
    val sets: String,

    @ColumnInfo(name = "todo_reps")
    val reps: String,

    @ColumnInfo(name = "todo_rest")
    val time: String,

    @ColumnInfo(name = "todo_weight")
    val weight: String,

    @ColumnInfo(name = "todo_muscle")
    val muscle: String,

    @ColumnInfo(name = "todo_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {}