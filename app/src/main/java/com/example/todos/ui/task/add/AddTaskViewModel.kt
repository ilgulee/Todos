package com.example.todos.ui.task.add

import androidx.lifecycle.ViewModel
import com.example.todos.data.model.Task
import com.example.todos.data.source.TaskRepository

class AddTaskViewModel : ViewModel() {
    private val repository = TaskRepository.getTaskRepository()
    fun saveTask(task: Task) {
        repository.addTask(task)
    }
}