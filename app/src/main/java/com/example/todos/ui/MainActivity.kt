package com.example.todos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todos.R
import com.example.todos.ui.task.TaskFragment
import com.example.todos.ui.tasks.TasksListFragment
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), TasksListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.i("onCreate starts")

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = TasksListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onTasksListItemSelected(taskId: UUID) {
        Timber.d("selected task's id is $taskId")
        val fragment = TaskFragment.newInstance(taskId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
