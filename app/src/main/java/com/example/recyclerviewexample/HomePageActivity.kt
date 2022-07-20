package com.example.recyclerviewexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var TYPE_TRAINING = 0

class HomePageActivity : AppCompatActivity() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.neumorphism)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        displayHistoryCountAndWipeTodo()
        // Resetting the global variables: Workout Notes and Type Of Training
        USER_NOTES = null
        TYPE_TRAINING = 0
        
//        Toast.makeText(this, "changed!", Toast.LENGTH_SHORT).show()
    }

    private fun displayHistoryCountAndWipeTodo() {
        lifecycleScope.launch(Dispatchers.IO) {
            val allTodos = todoViewModel.getListTodos()
            for(elements in allTodos) {
                todoViewModel.deleteTodo(elements)
            }
            val workoutHistorySize = historyViewModel.getListHistory().size
            val historyCount = findViewById<TextView>(R.id.historyCount)
            if (workoutHistorySize > 1) {
                historyCount.text = "${workoutHistorySize} workouts in total"
            } else {
                historyCount.text = "${workoutHistorySize} workout in total"
            }
        }
    }

    fun iconHome(view: View) {
        setContentView(R.layout.neumorphism)
        displayHistoryCountAndWipeTodo()
    }

    fun iconProfile(view: View) {
        setContentView(R.layout.neumorphism_profile)
    }

    fun iconChart(view: View) {

    }

    fun iconNotifications(view: View) {

    }

    fun viewHistories (view:View) {
        val intent = Intent(this, HistoryPageActivity::class.java)
        startActivity(intent)
    }

    fun punchTraining(view: View) {
        TYPE_TRAINING = 0
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
        Toast.makeText(this, "Start by inputting your exercises!!", Toast.LENGTH_LONG).show()
    }

    fun weightTraining(view: View) {
        TYPE_TRAINING = 1
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
        Toast.makeText(this, "Start by selecting your exercises!!", Toast.LENGTH_LONG).show()
    }

    fun runTraining(view: View) {
        TYPE_TRAINING = 2
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
        Toast.makeText(this, "Enter your Running details!!", Toast.LENGTH_LONG).show()
    }

    fun yogaTraining(view: View) {
        TYPE_TRAINING = 3
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
        Toast.makeText(this, "Start by inputting your exercises!!", Toast.LENGTH_LONG).show()
    }




}
