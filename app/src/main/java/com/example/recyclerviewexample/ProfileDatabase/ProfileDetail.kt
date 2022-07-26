package com.example.recyclerviewexample.ProfileDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_data_table")
data class ProfileDetail(
    @PrimaryKey
    @ColumnInfo(name ="profile_name")
    var profileName: String,

    @ColumnInfo(name = "profile_height")
    var profileHeight: String,

    @ColumnInfo(name = "profile_weight")
    var profileWeight: String,

    @ColumnInfo(name = "profile_gender")
    var profileGender: String
)
