package com.example.todos.ui.tasks

import androidx.lifecycle.ViewModel
import com.example.todos.data.source.TaskRepository

class TasksListViewModel : ViewModel() {
    private val repository = TaskRepository.getTaskRepository()
    val tasksListLiveData = repository.getTasks()
}