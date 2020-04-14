package com.example.todos.ui.tasks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.data.model.Task
import kotlinx.android.synthetic.main.fragment_tasks_list.*
import timber.log.Timber
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TasksListFragment : Fragment() {
    interface Callbacks {
        fun onTasksListItemSelected(taskId: UUID)
        fun onAddNewTaskFabClicked()
    }

    private var callbacks: Callbacks? = null
    private val viewModel: TasksListViewModel by lazy {
        ViewModelProvider(this).get(TasksListViewModel::class.java)
    }
    private lateinit var tasksListRecyclerView: RecyclerView
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Timber.d("Total tasks: ${tasksListViewModel.tasks.size}")
//    }

    companion object {
        fun newInstance(): TasksListFragment {
            return TasksListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tasks_list, container, false)
        tasksListRecyclerView = view.findViewById(R.id.tasks_list)
        tasksListRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksListRecyclerView.adapter = adapter
//        updateUI()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tasksListLiveData.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let {
                Timber.i("task liveData ${tasks.size}")
                updateUI(tasks)
            }
        })

        add_task_fab.setOnClickListener {
            callbacks?.onAddNewTaskFabClicked()
        }
    }

    private fun updateUI(tasks: List<Task>) {
//        val tasks = tasksListViewModel.tasks
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
//            Toast.makeText(context, "${task.taskTitle} clicked", Toast.LENGTH_SHORT).show()
            callbacks?.onTasksListItemSelected(task.taskId)
        }
    }
}
