package com.example.todos.app

import android.app.Application
import com.example.todos.BuildConfig
import com.example.todos.data.source.TaskRepository
import timber.log.Timber

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}