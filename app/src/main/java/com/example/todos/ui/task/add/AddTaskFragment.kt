package com.example.todos.ui.task.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todos.R
import com.example.todos.data.model.Task
import kotlinx.android.synthetic.main.fragment_add_task.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment() {

    private lateinit var task: Task
    private lateinit var taskTitle: EditText
    private lateinit var taskDetails: EditText
    private val viewModel: AddTaskViewModel by lazy {
        ViewModelProvider(this).get(AddTaskViewModel::class.java)
    }

    companion object {
        fun newInstance(): AddTaskFragment {
            return AddTaskFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        taskTitle = view.findViewById(R.id.task_title)
        taskDetails = view.findViewById(R.id.task_details)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_task_fab.setOnClickListener {
            Timber.i("New task is $task")
            viewModel.saveTask(task)
            activity?.let {
                it.onBackPressed()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val taskTitleWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                task.taskTitle = s.toString()
            }
        }
        taskTitle.addTextChangedListener(taskTitleWatcher)
        val taskDetailsWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                task.taskDescription = s.toString()
            }
        }
        taskDetails.addTextChangedListener(taskDetailsWatcher)
    }
}
