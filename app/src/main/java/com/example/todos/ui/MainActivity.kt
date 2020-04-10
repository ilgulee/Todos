package com.example.todos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todos.R
import com.example.todos.ui.tasks.TasksListFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

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
}
