package com.example.todos.ui.tasks

import androidx.lifecycle.ViewModel
import com.example.todos.data.model.Task
import com.example.todos.data.source.TaskRepository

class TasksListViewModel : ViewModel() {
    private val repository = TaskRepository.getTaskRepository()
    val tasksListLiveData = repository.getTasks()

    init {
        val stub1 = Task(
            taskTitle = "Mastering Android Programming",
            taskDescription = "First, mastering Kotlin. Second, mastering Jetpack.",
            isCompleted = false
        )
        repository.addTask(stub1)
        val stub2 = Task(
            taskTitle = "Build My Application",
            taskDescription = "Planning, coding...",
            isCompleted = true
        )
        repository.addTask(stub2)
    }
}