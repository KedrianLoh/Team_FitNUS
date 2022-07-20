package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var REUSE_WORKOUT = 0

class WorkoutHistoryInfoActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var viewModel1: TodoViewModel
    private lateinit var builder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.records_user_interface)
        supportActionBar?.hide()
//        supportActionBar?.title = "${WORKOUT_HISTORY_INFO!!.date}"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewDone2)
        val adapter = WorkoutHistoryInfoAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        builder = AlertDialog.Builder(this)
        convertTitles()
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(TodoViewModel::class.java)
    }

    override fun onBackPressed() {
        val intent = Intent(this, HistoryPageActivity::class.java)
        startActivity(intent)
    }

    private fun convertTitles() {
        val textFirst = findViewById<TextView>(R.id.textFirst)
        val textSecond = findViewById<TextView>(R.id.textSecond)
        val textThird = findViewById<TextView>(R.id.textThird)
        val textFirstTitle = findViewById<TextView>(R.id.textFirstTitle)
        val textSecondTitle = findViewById<TextView>(R.id.textSecondTitle)
        val textThirdTitle = findViewById<TextView>(R.id.textThirdTitle)

        when (WORKOUT_HISTORY_INFO!!.type) {
            0 -> {}

            1 -> {
                var totalRepsDone = 0
                var totalSets = 0
                for (elements in WORKOUT_HISTORY_INFO!!.arrayList) {
                    totalSets += elements.sets.toInt()
                    totalRepsDone += elements.reps.toInt()
                }
                textFirst.text = WORKOUT_HISTORY_INFO?.date?.substring(0, 10)
                textSecond.text = totalSets.toString()
                textThird.text = totalRepsDone.toString()
            }

            2 -> {
                textSecondTitle.text = "Distance (km)"
                textThirdTitle.text = "Duration (mins)"
                textFirst.text = WORKOUT_HISTORY_INFO?.date?.substring(0, 10)
                textSecond.text = WORKOUT_HISTORY_INFO!!.arrayList[0].weight
                textThird.text = WORKOUT_HISTORY_INFO!!.arrayList[0].time
            }

            3 -> {}
        }
    }


    fun deleteHistory() {
        viewModel.deleteHistory(WORKOUT_HISTORY_INFO!!)
        val intent = Intent(this, HistoryPageActivity::class.java)
        startActivity(intent)
    }

    fun reuseWorkout() {
        lifecycleScope.launch(Dispatchers.IO) {
            val array = WORKOUT_HISTORY_INFO!!.arrayList
            for (i in 0 until array.size) {
                viewModel1.insertTodo(array[i])
            }
            REUSE_WORKOUT = 1
        }
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
        finish()
//        Toast.makeText(this, "${WORKOUT_HISTORY_INFO!!.arrayList!!.size}", Toast.LENGTH_SHORT).show()
    }

    fun moreButton(view: View) {
        builder.setTitle("More options")
            .setMessage("What would you like to do?")
            .setCancelable(true)
            .setPositiveButton("Reuse") { _, it ->
                reuseWorkout()
                val intent = Intent(this, FinalPage::class.java)
                startActivity(intent)
                Toast.makeText(
                    this,
                    "Workout Reused",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNeutralButton("Delete") { _, it ->
                deleteHistory()
                val intent = Intent(this, HistoryPageActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialogInterface, it ->
                dialogInterface.cancel()
            }.show()
    }
}