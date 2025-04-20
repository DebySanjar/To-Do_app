🚀 Cyberpunk To-Do: Conquer Your Tasks with Neon Precision
Welcome to Cyberpunk To-Do, a sleek and futuristic task management app designed to obliterate procrastination with neon-fueled motivation! Built with Kotlin, MVVM architecture, and a touch of cyberpunk aesthetics, this app transforms your daily tasks into a thrilling mission to conquer laziness. Whether you're scheduling a workout, planning a study session, or chasing deadlines, this app ensures you stay on track with timely notifications and an intuitive interface.


🌌 Features That Ignite Productivity
Neon-Charged Task Creation: Add tasks effortlessly via a Floating Action Button (FAB) that opens a dialog with fields for title, description, and a time picker for scheduling.
Real-Time Notifications: Get pinged at the exact moment your task is due with customizable notifications powered by WorkManager. Never miss a deadline again!
Slick Task Management: View, complete, or delete tasks in a vibrant RecyclerView with smooth animations and progress tracking.
Offline Storage: Tasks are securely stored using ROOM, ensuring your data persists even when you're off the grid.
Cyberpunk Aesthetics: Inspired by neon blues and pinks, the UI is designed to make task management feel like a sci-fi adventure.
Scalable Architecture: Built with MVVM, ViewBinding, and DiffUtil for performance and easy extensibility.

🛠 Tech Stack

Language: Kotlin
Architecture: MVVM (Model-View-ViewModel)
UI: ViewBinding, RecyclerView, Material Design Components
Database: ROOM for persistent storage
Background Tasks: WorkManager for scheduling notifications

Dependencies:
androidx.room for database operations
androidx.work for notifications
androidx.recyclerview for task list rendering
com.google.android.material for FAB and dialogs


📂 Project Structure

CyberpunkToDo/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/codezone/aniqmasloyiha/
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   └── Task.kt               # Task model with title, description, time
│   │   │   │   │   ├── room/
│   │   │   │   │   │   ├── AppDatabase.kt        # ROOM database configuration
│   │   │   │   │   │   ├── TaskDao.kt            # Data Access Object for tasks
│   │   │   │   ├── ui/
│   │   │   │   │   ├── adapter/
│   │   │   │   │   │   └── TaskAdapter.kt        # RecyclerView adapter for tasks
│   │   │   │   │   ├── view/
│   │   │   │   │   │   └── MainActivity.kt       # Main UI with FAB and dialog
│   │   │   │   │   ├── viewmodel/
│   │   │   │   │   │   └── TaskViewModel.kt      # ViewModel for business logic
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml         # Main screen with RecyclerView and FAB
│   │   │   │   │   ├── item_task.xml             # Task card layout
│   │   │   │   │   ├── dialog_add_task.xml       # Dialog for adding tasks
│   │   │   ├── AndroidManifest.xml               # App permissions and components
├── NotificationWorker.kt                         # Worker for scheduling notifications
├── build.gradle.kts (app)                        # Gradle dependencies and config

🚀  Install apk link : https://t.me/qidirsa_chiqadi/86
