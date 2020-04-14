package com.example.todos.ui.task.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todos.data.model.Task
import com.example.todos.data.source.TaskRepository
import java.util.*

class EditTaskViewModel : ViewModel() {
    private val repository = TaskRepository.getTaskRepository()
    private val taskIdLiveData = MutableLiveData<UUID>()
    var taskLiveData: LiveData<Task?> = Transformations.switchMap(taskIdLiveData) { taskId ->
        repository.getTask(taskId)
    }

    fun loadTask(taskId: UUID) {
        taskIdLiveData.value = taskId
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
    }
}