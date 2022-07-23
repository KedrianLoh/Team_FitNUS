package com.example.recyclerviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail
import com.example.recyclerviewexample.TodoDatabase.TodoDetail

class WorkoutHistoryInfoAdapter(private val listener: WorkoutHistoryInfoActivity) :
    RecyclerView.Adapter<WorkoutHistoryInfoAdapter.FinalViewHolder>() {
    private val allHistory = WORKOUT_HISTORY_INFO!!.arrayList


    class FinalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.textTitle)
        val textView2: TextView = itemView.findViewById(R.id.textMuscle)
        val imageView1: ImageView = itemView.findViewById(R.id.displayMuscle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalViewHolder {
        val viewHolder = FinalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item_2, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: FinalViewHolder, position: Int) {
        val currentItem = allHistory!![position]
        holder.textView1.text = currentItem.name
        when (WORKOUT_HISTORY_INFO!!.type) {

            0 -> {
                holder.textView2.text = currentItem.muscle
                if (currentItem.muscle == "Upper body") {
                    holder.imageView1.setImageResource(R.drawable.ic_punch2)
                } else if (currentItem.muscle == "Lower body") {
                    holder.imageView1.setImageResource(R.drawable.ic_kicking2)
                } else {
                    holder.imageView1.setImageResource(R.drawable.ic_fullbody)
                }
            }

            1 -> {
                holder.textView2.text =
                    "Sets: ${currentItem.sets} | Reps: ${currentItem.reps} | Rest: ${currentItem.time} | Weight: ${currentItem.weight}kg"
                when (currentItem.muscle) {
                    "Chest" -> holder.imageView1.setImageResource(R.drawable.ic_chest)
                    "Back" -> holder.imageView1.setImageResource(R.drawable.ic_back)
                    "Shoulders" -> holder.imageView1.setImageResource(R.drawable.ic_shoulder)
                    "Abs" -> holder.imageView1.setImageResource(R.drawable.ic_abs)
                    "Arms" -> holder.imageView1.setImageResource(R.drawable.ic_arms)
                    "Legs" -> holder.imageView1.setImageResource(R.drawable.ic_legs)
                    "Full body" -> holder.imageView1.setImageResource(R.drawable.ic_fullbody2)
                }
            }

            2 -> {
                holder.textView2.text = "Start: ${currentItem.sets} | End : ${currentItem.reps}"
                holder.imageView1.setImageResource(R.drawable.ic_run2)
            }

            3 -> {
                holder.textView2.text = currentItem.muscle
                if (currentItem.muscle == "Upper body") {
                    holder.imageView1.setImageResource(R.drawable.ic_yogaupper)
                } else if (currentItem.muscle == "Lower body") {
                    holder.imageView1.setImageResource(R.drawable.ic_yogalower)
                } else {
                    holder.imageView1.setImageResource(R.drawable.ic_yogafull)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return allHistory!!.size
    }
}
