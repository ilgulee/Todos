package com.example.todos.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.data.Task
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class TasksListFragment : Fragment() {

    private val tasksListViewModel: TasksListViewModel by lazy {
        ViewModelProvider(this).get(TasksListViewModel::class.java)
    }
    private lateinit var tasksListRecyclerView: RecyclerView
    private var adapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Total tasks: ${tasksListViewModel.tasks.size}")
    }

    companion object {
        fun newInstance(): TasksListFragment {
            return TasksListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tasks_list, container, false)
        tasksListRecyclerView = view.findViewById(R.id.tasks_list)
        tasksListRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }

    private fun updateUI() {
        val tasks = tasksListViewModel.tasks
        adapter = TaskAdapter(tasks)
        tasksListRecyclerView.adapter = adapter
    }

    private inner class TaskAdapter(var tasks: List<Task>) :
        RecyclerView.Adapter<TaskViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_task, parent, false)
            return TaskViewHolder(view)
        }

        override fun getItemCount(): Int {
            return tasks.size
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasks[position]
            holder.apply {
                taskTitle.text = task.taskTitle
                completeCheckbox.isChecked = task.isCompleted
            }
        }

    }

    private inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle = itemView.findViewById<TextView>(R.id.task_title)
        val completeCheckbox = itemView.findViewById<CheckBox>(R.id.complete_checkbox)
    }
}
