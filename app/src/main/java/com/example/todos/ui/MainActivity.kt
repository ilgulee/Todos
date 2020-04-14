package com.example.todos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todos.R
import com.example.todos.ui.task.add.AddTaskFragment
import com.example.todos.ui.task.edit.EditTaskFragment
import com.example.todos.ui.tasks.TasksListFragment
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(),
    TasksListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.i("onCreate starts")

        updateUI()
    }

    private fun updateUI() {
        Timber.i("updateUI starts")
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            Timber.i("current fragment is null")
            val fragment = TasksListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onTasksListItemSelected(taskId: UUID) {
        Timber.d("on task list, selected task's id is $taskId")
        val fragment = EditTaskFragment.newInstance(taskId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onAddNewTaskFabClicked() {
        Timber.i("on add new task fab starts")
        val fragment = AddTaskFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
