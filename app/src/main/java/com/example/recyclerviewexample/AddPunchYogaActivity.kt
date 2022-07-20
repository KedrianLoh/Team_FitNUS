package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import kotlinx.android.synthetic.main.activity_add_punch_yoga.*

class AddPunchYogaActivity : AppCompatActivity() {

    private lateinit var todoViewModel : TodoViewModel

    override fun onResume() {
        super.onResume()
        val targetMuscle = resources.getStringArray(R.array.TargetBody)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, targetMuscle)
        val autoCompleteView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autoCompleteView.setAdapter(arrayAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_punch_yoga)
        supportActionBar?.hide()
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        addNewItem.setOnClickListener {
            val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            val exerciseName = insertExerciseName.text.toString()
            val bodyInvolved = autoCompleteTextView.text.toString()
            if(exerciseName.isNotEmpty()) {
                val exercise = TodoDetail(exerciseName, "NIL", "NIL", "NIL", "NIL", bodyInvolved, 0)
                todoViewModel.insertTodo(exercise)
                Toast.makeText(this, "${exerciseName} added!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, FinalPage::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Empty Exercise Name!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}