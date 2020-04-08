package com.example.todos.tasks

import androidx.lifecycle.ViewModel
import com.example.todos.data.Task

class TasksListViewModel : ViewModel() {
    val tasks = mutableListOf<Task>()

    init {
        for (index in 0 until 100) {
            val task = Task()
            task.taskTitle = "Task #$index"
            task.isCompleted = index % 3 == 0
            tasks.add(task)
        }
    }
}