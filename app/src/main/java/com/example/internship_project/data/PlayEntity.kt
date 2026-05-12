package com.example.internship_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "play")
data class PlayEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val duration: String,
    val posterUrl: String
)
