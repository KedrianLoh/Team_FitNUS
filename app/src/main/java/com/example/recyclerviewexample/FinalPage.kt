package com.example.recyclerviewexample

import android.content.Intent
import android.database.Cursor
import android.media.Image
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import kotlinx.android.synthetic.main.activity_final_page.*
import kotlinx.android.synthetic.main.default_page.*
import kotlinx.android.synthetic.main.run_default_page.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

var INDEX = 9
var USER_NOTES: String? = null
var EDIT_BUTTON_CLICKED = false

class FinalPage : AppCompatActivity(), FinalPageAdapter.IAdapter {

    // we have done this the previous time
    // now let us define view model in our main activity
    private lateinit var viewModel: TodoViewModel
    private lateinit var viewModel1: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

//        initialize viewmodel
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(HistoryViewModel::class.java)

        when (TYPE_TRAINING) {
            0, 1, 3 -> {
                setContentView(R.layout.default_page)
                val recyclerView: RecyclerView = findViewById(R.id.defaultPageRecyclerView)
                val adapter = FinalPageAdapter(this)
                recyclerView.adapter = adapter // adapter for recycler view
                recyclerView.layoutManager = LinearLayoutManager(this) // defines horizontal layout

//         set the viewmodel to update our recyclerview adapter
                viewModel.allTodoDetail.observe(this, Observer {
                    adapter.updateData(it)
                })
                alterINDEX()
            }

            2 -> {
                setContentView(R.layout.run_default_page)
            }

        }

        alterUI()
    }


    private fun updateNotesBeforeLeavingPage() {
        val notesText: String = findViewById<EditText>(R.id.exerciseNotes).text.toString()
        USER_NOTES = notesText
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }

    private fun alterINDEX() {
        lifecycleScope.launch(Dispatchers.IO) {
            val allTodos = viewModel.getListTodos()
            if (allTodos.isNotEmpty()) {
                INDEX = 0
            } else {
                INDEX = 9
            }
        }
    }

    private fun alterUI() {
        val notesUI = findViewById<EditText>(R.id.exerciseNotes)
        notesUI.setText(USER_NOTES)
        val date = Date()
        val icon: ImageView = findViewById(R.id.typeExercise)
        val typeExerciseName = findViewById<TextView>(R.id.typeExerciseName)
        val dateUI = findViewById<TextView>(R.id.date)
        val dateDisplay = sdf.format(date).substring(0, 10)
        dateUI.text = "${dateDisplay}\n"

        val leftButton = findViewById<Button>(R.id.leftButton)
        val rightButton = findViewById<Button>(R.id.rightButton)
        when (TYPE_TRAINING) {
            0 -> {
                icon.setImageResource(R.drawable.ic_punch)
                typeExerciseName.text = "Punch Training"
                leftButton.text = "Input Exercise"
                rightButton.text = "Record Workout"
            }
            1 -> {
                icon.setImageResource(R.drawable.ic_weight)
                typeExerciseName.text = "Weight Training"
            }
            2 -> {
                icon.setImageResource(R.drawable.ic_run)
                typeExerciseName.text = "Run Training"
            }
            3 -> {
                icon.setImageResource(R.drawable.ic_yoga)
                typeExerciseName.text = "Yoga Training"
                leftButton.text = "Input Exercise"
                rightButton.text = "Record Workout"
            }
        }

    }

    fun startWorkout(view: View) {
        val notes = findViewById<EditText>(R.id.exerciseNotes)
        USER_NOTES = notes.text.toString()
        when (TYPE_TRAINING) {
            0, 3 -> {
                lifecycleScope.launch (Dispatchers.IO) {
                    addHistory()
                    val allTodos = viewModel.getListTodos()
                    for(elements in allTodos) {
                        viewModel.deleteTodo(elements)
                    }
                }
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            }

            1 -> {
                if (INDEX == 0) {
                    updateNotesBeforeLeavingPage()
                    val intent = Intent(this@FinalPage, PrepExercise::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    fun selectExercise(view: View) {
        updateNotesBeforeLeavingPage()
        if (TYPE_TRAINING == 1) {
            val intent = Intent(this, SelectPageActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AddPunchYogaActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onIconClick(todoDetail: TodoDetail) {
        viewModel.deleteTodo(todoDetail)
    }

    override fun onEditIconClick(todoDetail: TodoDetail) {
        val itemNameToPass: String = todoDetail.name
        DETAIL_COUNT = 2
        val intent = Intent(this, DetailExerciseActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, itemNameToPass)
        }
        startActivity(intent)
    }

    suspend fun addHistory() {
        val allTodos = viewModel.getListTodos() // Gets the list of Todos at Final Page
        val date = Date()
        if (allTodos.isNotEmpty()) {
            viewModel1.insertHistory(
                HistoryDetail(
                    allTodos as ArrayList<TodoDetail>,
                    0,
                    "${sdf.format(date)}",
                    TYPE_TRAINING,
                    USER_NOTES!!
                )
            )
        }
    }

    fun recordRun(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            val editText1 = findViewById<EditText>(R.id.inputStart).text.toString()
            val editText2 = findViewById<EditText>(R.id.inputEnd).text.toString()
            val editText3 = findViewById<EditText>(R.id.inputDistance).text.toString()
            val editText4 = findViewById<EditText>(R.id.inputTime).text.toString()
            USER_NOTES = findViewById<EditText>(R.id.exerciseNotes).text.toString()
            val insertingTodo =
                TodoDetail("Run", editText1, editText2, editText4, editText3, "runx", 0)
            viewModel.insertTodo(insertingTodo)
            delay(100)
            addHistory()
        }
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                if (REUSE_WORKOUT == 1) {
//                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        val allTodos = viewModel.getListTodos()
//                        for (element in allTodos) {
//                            viewModel.deleteTodo(element)
//                        }
//                    }
//                    REUSE_WORKOUT = 0
//                }
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//        val abc : ArrayList<TodoDetail> = ArrayList()
//        val x : TodoDetail = TodoDetail("x", "1", "1", "1")
//        abc.add(x)
//         abc.add(x)
//        viewModel1.insertHistory(HistoryDetail(abc, 0))
//    }

}