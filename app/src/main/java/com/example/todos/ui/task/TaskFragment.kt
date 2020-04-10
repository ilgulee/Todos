package com.example.todos.ui.task

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.todos.R
import com.example.todos.data.model.Task
import timber.log.Timber
import java.util.*

/**
 * A simple [Fragment] subclass.
 */

private const val ARG_TASK_ID = "taskId"

class TaskFragment : Fragment() {
    private lateinit var task: Task
    private lateinit var taskTitle: EditText
    private lateinit var taskDetails: EditText

    companion object {
        fun newInstance(taskId: UUID): TaskFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TASK_ID, taskId)
            }
            return TaskFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
        val taskId = arguments?.getSerializable(ARG_TASK_ID) as UUID
        Timber.i("retrieved args bundle taskId is $taskId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        taskTitle = view.findViewById(R.id.task_title)
        taskDetails = view.findViewById(R.id.task_details)
        return view
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
