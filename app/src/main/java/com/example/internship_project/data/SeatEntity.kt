package com.example.internship_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seats")
data class SeatEntity(
    @PrimaryKey val id: String,
    val row: Int,
    val column: Int,
    val isReserved: Boolean
)
