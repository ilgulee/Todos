package com.example.todos.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
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
            holder.bind(task)
        }

    }

    private inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var task: Task
        val taskTitle: TextView = itemView.findViewById(R.id.task_title)
        val completeCheckbox: CheckBox = itemView.findViewById(R.id.complete_checkbox)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            taskTitle.text = this.task.taskTitle
            completeCheckbox.isChecked = this.task.isCompleted
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${task.taskTitle} clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
