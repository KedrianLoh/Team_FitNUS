package com.example.recyclerviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail

class HistoryPageAdapter(
    private val listener: HistoryPageActivity
) : RecyclerView.Adapter<HistoryPageAdapter.ExampleViewHolder>() {

    val allHistory = ArrayList<HistoryDetail>()

    inner class ExampleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val textView1: TextView = itemView.findViewById(R.id.textTitle)
        val textView2: TextView = itemView.findViewById(R.id.textMuscle)
        val imageView1: ImageView = itemView.findViewById(R.id.displayMuscle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.history_exercise_item_1,
            parent,
            false
        )
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = allHistory[position]
        val date = currentItem.date.substring(0,10)
        holder.textView1.text = "Workout on ${date}"
        holder.textView2.text = currentItem.notes
        when (currentItem.type) {
            0 -> holder.imageView1.setImageResource(R.drawable.ic_punch)
            1 -> holder.imageView1.setImageResource(R.drawable.ic_weight)
            2 -> holder.imageView1.setImageResource(R.drawable.ic_run)
            3 -> holder.imageView1.setImageResource(R.drawable.ic_yoga)
        }
    }

    override fun getItemCount() = allHistory.size

    fun updateData(newData: List<HistoryDetail>) {
        allHistory.clear()
        allHistory.addAll(newData)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}