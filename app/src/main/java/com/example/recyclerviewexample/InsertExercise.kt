package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.database.ExerciseDetail
import kotlinx.android.synthetic.main.activity_insert_exercise.*

class InsertExercise : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel

    override fun onResume() {
        super.onResume()
        val targetMuscle = resources.getStringArray(R.array.TargetMuscle)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, targetMuscle)
        val autoCompleteView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autoCompleteView.setAdapter(arrayAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_exercise)

        viewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        insertNewItem.setOnClickListener {
            val autoCompleteView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            val exerciseName = insertExerciseName.text.toString()
            val muscleInvolved = autoCompleteView.text.toString()
            if (exerciseName.isNotEmpty()) {
                viewModel.insertExercise(ExerciseDetail(exerciseName, muscleInvolved, 1))
                val intent = Intent(this, SelectPageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}