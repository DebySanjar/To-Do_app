package com.codezone.aniqmasloyiha.ui.view

import android.Manifest
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.codezone.aniqmasloyiha.NotificationWorker
import com.codezone.aniqmasloyiha.data.model.Task
import com.codezone.aniqmasloyiha.data.room.AppDatabase
import com.codezone.aniqmasloyiha.databinding.ActivityMainBinding
import com.codezone.aniqmasloyiha.databinding.DialogAddTaskBinding
import com.codezone.aniqmasloyiha.ui.adapter.TaskAdapter
import com.codezone.aniqmasloyiha.ui.viewmodel.TaskViewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit
import android.app.NotificationChannel
import android.app.NotificationManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private val viewModel: TaskViewModel by viewModels {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "app_db").build()
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskViewModel(db.taskDao()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        setupRecyclerView()
        setupObservers()
        setupFabClickListener()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Notifications"
            val descriptionText = "Notifications for task reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("task_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onCompleteClick = { task ->
                viewModel.updateTask(task.copy(isCompleted = true, progress = 100))
            },
            onDeleteClick = { task ->
                viewModel.deleteTask(task)
            }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.tasks.observe(this) { tasks ->
            adapter.updateTasks(tasks)
        }
    }

    private fun setupFabClickListener() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnSelectTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                dialogBinding.etTaskTime.setText(time)
            }, hour, minute, true).show()
        }

        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etTaskTitle.text.toString()
            val description = dialogBinding.etTaskDescription.text.toString()
            val taskTime = dialogBinding.etTaskTime.text.toString()

            if (title.isNotEmpty() && taskTime.isNotEmpty()) {
                val task = Task(title = title, description = description, taskTime = taskTime)
                viewModel.addTask(title, description, taskTime)
                scheduleNotification(task) // Notifikatsiyani rejalashtirish
                dialog.dismiss()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun scheduleNotification(task: Task) {
        val timeParts = task.taskTime.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val delay = calendar.timeInMillis - System.currentTimeMillis()
        if (delay > 0) {
            val data = workDataOf("title" to task.title)
            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()
            WorkManager.getInstance(this).enqueue(workRequest)
        }
    }
}