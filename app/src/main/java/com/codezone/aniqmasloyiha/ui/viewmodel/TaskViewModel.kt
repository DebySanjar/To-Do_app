package com.codezone.aniqmasloyiha.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codezone.aniqmasloyiha.data.model.Task
import com.codezone.aniqmasloyiha.data.room.TaskDao
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {
    val tasks: LiveData<List<Task>> = dao.getAllTasks().asLiveData()

    fun addTask(title: String, description: String, taskTime: String) {
        viewModelScope.launch {
            dao.insert(Task(title = title, description = description, taskTime = taskTime))
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            dao.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.delete(task)
        }
    }
}