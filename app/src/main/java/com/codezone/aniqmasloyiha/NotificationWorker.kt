package com.codezone.aniqmasloyiha

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(appContext: Context, params: WorkerParameters) :
    Worker(appContext, params) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Vazifa"
        val notification = NotificationCompat.Builder(applicationContext, "task_channel")
            .setContentTitle("Vazifa vaqti!")
            .setContentText("$title vaqti keldi!")
            .setSmallIcon(R.drawable.notification) // Maxsus ikona
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(System.currentTimeMillis().toInt(), notification)
        return Result.success()
    }
}