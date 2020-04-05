package com.example.todos.data

import java.util.*

data class Task(
    val taskId: UUID = UUID.randomUUID(),
    var taskTitle: String = "",
    var taskDescription: String = "",
    var isCompleted: Boolean = false,
    var date: Date = Date()
)