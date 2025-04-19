package com.codezone.aniqmasloyiha.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val taskTime: String, // Vaqt, masalan, "14:30"
    val isCompleted: Boolean = false,
    val progress: Int = 0 // Progress foizi (0-100)
)