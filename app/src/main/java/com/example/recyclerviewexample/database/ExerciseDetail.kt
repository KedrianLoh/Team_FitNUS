package com.example.recyclerviewexample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_data_table")
data class ExerciseDetail(
    @PrimaryKey
    @ColumnInfo(name = "exercise_name")
    var nameOfExercise: String,

    @ColumnInfo(name = "body_part")
    var muscleInvolved: String,

    @ColumnInfo(name = "default")
    var canDelete: Int
) {}


