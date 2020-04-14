package com.example.todos.ui.task.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todos.R
import com.example.todos.data.model.Task
import kotlinx.android.synthetic.main.fragment_edit_task.*
import timber.log.Timber
import java.util.*

/**
 * A simple [Fragment] subclass.
 */

private const val ARG_TASK_ID = "taskId"

class EditTaskFragment : Fragment() {

    private lateinit var task: Task
    private lateinit var taskTitle: EditText
    private lateinit var taskDetails: EditText

    private val viewModel: EditTaskViewModel by lazy {
        ViewModelProvider(this).get(EditTaskViewModel::class.java)
    }

    companion object {
        fun newInstance(taskId: UUID): EditTaskFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TASK_ID, taskId)
            }
            return EditTaskFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
        val taskId = arguments?.getSerializable(ARG_TASK_ID) as UUID
        Timber.i("retrieved args bundle taskId is $taskId")
        viewModel.loadTask(taskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)
        taskTitle = view.findViewById(R.id.task_title)
        taskDetails = view.findViewById(R.id.task_details)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.taskLiveData.observe(viewLifecycleOwner, Observer { task ->
            task?.let {
                this.task = task
                updateUI()
            }
        })

        add_task_fab.setOnClickListener {
            task.isCompleted = complete_check_box.isChecked
            if (task.isCompleted) {
                task.endTimeMilli = System.currentTimeMillis()
            } else {
                task.endTimeMilli = task.startTimeMilli
            }
            Timber.d("updated task is $task")
            viewModel.updateTask(task)
            activity?.let {
                it.onBackPressed()
            }
        }
    }

    private fun updateUI() {
        taskTitle.setText(task.taskTitle)
        taskDetails.setText(task.taskDescription)
        complete_check_box.apply {
            isChecked = task.isCompleted
            jumpDrawablesToCurrentState()
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
