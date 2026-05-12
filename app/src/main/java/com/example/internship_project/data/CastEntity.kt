package com.example.internship_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast_members")
data class CastEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String
)
