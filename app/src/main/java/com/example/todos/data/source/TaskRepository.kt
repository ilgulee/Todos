package com.example.todos.data.source

import android.app.Application
import androidx.room.Room
import com.example.todos.data.model.Task
import com.example.todos.data.source.local.TaskDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "task_database"

class TaskRepository private constructor(application: Application) {
    private val database =
        Room.databaseBuilder(
            application.applicationContext,
            TaskDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    private val taskDao = database.taskDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getTasks() = taskDao.getTasks()
    fun getTask(id: UUID) = taskDao.getTask(id)
    fun addTask(task: Task) {
        executor.execute {
            taskDao.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        executor.execute {
            taskDao.updateTask(task)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TaskRepository? = null

        fun initialize(app: Application) {
            if (INSTANCE == null) {
                synchronized(this) {
                    TaskRepository(app).also {
                        INSTANCE = it
                    }
                }
            }
        }

        fun getTaskRepository(): TaskRepository {
            return INSTANCE
                ?: throw IllegalStateException("TaskRepository needs to be initialized.")
        }
    }
}