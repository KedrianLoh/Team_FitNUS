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
                if (currentItem.muscle == "upper body") {
                    holder.imageView1.setImageResource(R.drawable.ic_punch)
                } else if (currentItem.muscle == "lower body") {
                    holder.imageView1.setImageResource(R.drawable.ic_kicking)
                } else {
                    holder.imageView1.setImageResource(R.drawable.ic_fullbody)
                }
            }

            1 -> {
                holder.textView2.text =
                    "Sets: ${currentItem.sets} | Reps: ${currentItem.reps} | Rest: ${currentItem.time}"
                when (currentItem.muscle) {
                    "chest" -> holder.imageView1.setImageResource(R.drawable.ic_chest)
                    "back" -> holder.imageView1.setImageResource(R.drawable.ic_back)
                    "shoulders" -> holder.imageView1.setImageResource(R.drawable.ic_shoulder)
                    "abs" -> holder.imageView1.setImageResource(R.drawable.ic_abs)
                    "arms" -> holder.imageView1.setImageResource(R.drawable.ic_arms)
                    "legs" -> holder.imageView1.setImageResource(R.drawable.ic_legs)
                }
            }

            2 -> {
                holder.textView2.text = "Start: ${currentItem.sets} | End : ${currentItem.reps}"
                holder.imageView1.setImageResource(R.drawable.ic_run)
            }

            3 -> {
                holder.textView2.text = currentItem.muscle
                if (currentItem.muscle == "upper body") {
                    holder.imageView1.setImageResource(R.drawable.ic_yogaupper)
                } else if (currentItem.muscle == "lower body") {
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
