package com.example.todos.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todos.data.model.Task
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY start_date DESC")
    fun getTasks(): LiveData<List<Task>>
//    fun getTasks(): List<Task>

    @Query("SELECT * FROM task_table WHERE id = (:id)")
    fun getTask(id: UUID): LiveData<Task?>
//    fun getTask(id:UUID): Task?

    @Insert
    fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)
}
