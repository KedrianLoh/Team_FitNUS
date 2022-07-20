package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import kotlinx.android.synthetic.main.activity_prep_exercise.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var RESTCOUNT: Long = 3100
var TOTALEXERCISESIZE: Int = 1
class PrepExercise : AppCompatActivity() {
    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prep_exercise)
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            val allTodos = viewModel.getListTodos()
            runOnUiThread {
                nameView.text = allTodos[INDEX].name
                val sets = allTodos[INDEX].sets
                setsView.text = "${sets} sets"
                val reps = allTodos[INDEX].reps
                repsView.text = "${reps} reps"
                RESTCOUNT = ((allTodos[INDEX].time.toFloat()) * 60000).toLong()
                val rest = allTodos[INDEX].time.toFloat()
                restView.text = "${rest} min"
                TOTALEXERCISESIZE = allTodos.size
            }
        }
        startEx.setOnClickListener {
            if (INDEX < TOTALEXERCISESIZE) {
                val intent = Intent(this@PrepExercise, StartExercise::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@PrepExercise, FinalPage::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}