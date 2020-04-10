package com.example.todos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey @ColumnInfo(name = "id") val taskId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") var taskTitle: String = "",
    @ColumnInfo(name = "description") var taskDescription: String = "",
    @ColumnInfo(name = "completed") var isCompleted: Boolean = false,
    @ColumnInfo(name = "start_date") var date: Date = Date(),
    @ColumnInfo(name = "start_time_milli") var startTimeMilli: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time_milli") var endTimeMilli: Long = startTimeMilli
)