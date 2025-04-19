package com.codezone.aniqmasloyiha.ui.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codezone.aniqmasloyiha.data.model.Task
import com.codezone.aniqmasloyiha.databinding.ItemTaskBinding

class TaskAdapter(
    private val onCompleteClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val usedColors = mutableSetOf<Int>()

    private fun randomBrightColor(): Int {

        val rnd = java.util.Random()
        var color: Int
        do {
            val r = 100 + rnd.nextInt(156)
            val g = 100 + rnd.nextInt(156)
            val b = 100 + rnd.nextInt(156)
            color = android.graphics.Color.rgb(r, g, b)
        } while (usedColors.contains(color))
        usedColors.add(color)
        return color
    }
    private var tasks = listOf<Task>()

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.binding.tvTitle.text = task.title
        holder.binding.tvDescription.text = task.description
        holder.binding.tvTaskTime.text = task.taskTime
        holder.binding.progressBar.progress = task.progress
        holder.binding.btnComplete.setOnClickListener { onCompleteClick(task) }
        holder.binding.btnDelete.setOnClickListener { onDeleteClick(task) }

        val bgColor = randomBrightColor()
        holder.binding.cardview.setCardBackgroundColor(bgColor)
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        val diffCallback = TaskDiffCallback(tasks, newTasks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tasks = newTasks
        usedColors.clear()
        diffResult.dispatchUpdatesTo(this)
    }


    private class TaskDiffCallback(
        private val oldList: List<Task>,
        private val newList: List<Task>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}