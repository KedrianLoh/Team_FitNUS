package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail
import com.example.recyclerviewexample.HistoryDatabase.WorkoutHistoryDatabase
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import com.example.recyclerviewexample.database.ExerciseDetail
import kotlinx.android.synthetic.main.activity_detail_exercise.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class DetailExerciseActivity : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var viewModel1: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_exercise_1)
        supportActionBar?.hide()
        // initialize viewmodel
        viewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(TodoViewModel::class.java)
        val btn: Button = findViewById(R.id.add_detailed_button)
//        btn.setOnClickListener {
//            addingExercise()
//        }
//        val itemId: Int = intent.getStringExtra(EXTRA_MESSAGE)!!.toInt()
        if (DETAIL_COUNT == 1) {
            val itemName: String = intent.getStringExtra(EXTRA_MESSAGE)!!
            val itemMuscle: String = intent.getStringExtra(EXTRA_MESSAGE2)!!
            lifecycleScope.launch(Dispatchers.IO) {
                btn.text = "Add Exercise !"
                val exerciseName = findViewById<TextView>(R.id.exerciseName)
                exerciseName.text = itemName
            }
            btn.setOnClickListener {
                addingExercise()
            }
        } else if (DETAIL_COUNT == 2) {
            val itemName: String = intent.getStringExtra(EXTRA_MESSAGE)!!
            lifecycleScope.launch(Dispatchers.IO) {
                btn.text = "Update"
                exerciseName.text = itemName
                val todo = viewModel1.getTodoDetail(itemName)
                numberOfSets.setText(todo.sets)
                numberOfReps.setText(todo.reps)
                restInterval.setText(todo.time)
                weight.setText(todo.weight)
            }
            btn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    updatingExercise(itemName)
                }
            }
        }
    }
    override fun onBackPressed() {
        if (DETAIL_COUNT == 1) {
            val intent = Intent(this, SelectPageActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, FinalPage::class.java)
            startActivity(intent)
        }
    }

    private suspend fun updatingExercise(itemName: String) {
        val editText1: TextView = findViewById(R.id.exerciseName)
        val editText2: EditText = findViewById(R.id.numberOfSets)
        val editText3: EditText = findViewById(R.id.numberOfReps)
        val editText4: EditText = findViewById(R.id.restInterval)
        val editText5: EditText = findViewById(R.id.weight)
        val input1 = editText1.text.toString()
        val input2 = editText2.text.toString()
        val input3 = editText3.text.toString()
        val input4 = editText4.text.toString()
        val input5 = editText5.text.toString()
        if (input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty() && input5.isNotEmpty()) {
            val exerciseId = viewModel1.getTodoDetail(itemName).id
            val exerciseMuscle = viewModel1.getTodoDetail(itemName).muscle
            viewModel1.updateTodo(TodoDetail(input1, input2, input3, input4, input5, exerciseMuscle, exerciseId))
            val intent = Intent(this, FinalPage::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Missing Details!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addingExercise() {
        // set up add exercise function
        val editText1: TextView = findViewById(R.id.exerciseName)
        val editText2: EditText = findViewById(R.id.numberOfSets)
        val editText3: EditText = findViewById(R.id.numberOfReps)
        val editText4: EditText = findViewById(R.id.restInterval)
        val editText5: EditText = findViewById(R.id.weight)
        val input1 = editText1.text.toString()
        val input2 = editText2.text.toString()
        val input3 = editText3.text.toString()
        val input4 = editText4.text.toString()
        val input5 = editText5.text.toString()
        val input6 = intent.getStringExtra(EXTRA_MESSAGE2)!!
        if (input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty() && input5.isNotEmpty()) {
            // we need viewmodel here
            viewModel1.insertTodo(TodoDetail(input1, input2, input3, input4, input5, input6))
            Toast.makeText(this, "$input1 successfully added", Toast.LENGTH_SHORT).show()
            // move to activity
            val intent = Intent(this, SelectPageActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Missing Details!", Toast.LENGTH_SHORT).show()
        }
    }

}

