package com.example.recyclerviewexample

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail
import com.example.recyclerviewexample.TodoDatabase.TodoDetail
import kotlinx.android.synthetic.main.activity_start_exercise.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd/MM/yyyy   HH:mm:ss")

class StartExercise : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var viewModel1: HistoryViewModel
    private lateinit var timer: CountDownTimer
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(HistoryViewModel::class.java)
        builder = AlertDialog.Builder(this)

        /*Configuration of rest timer*/
        timer = object : CountDownTimer(RESTCOUNT, 1) {

            //Setting timer behaviour when time is counting.
            override fun onTick(millisUntilFinished: Long) {
                completeSet.visibility = View.INVISIBLE
                countdownMilis.visibility = View.VISIBLE
                skipRest.visibility = View.VISIBLE
                val minutes = millisUntilFinished / 60000
                val seconds = ((millisUntilFinished - minutes * 60000) / 1000)
                if (minutes < 10) {
                    val minuteString = minutes.toString()
                    countdownMinutes.text = "0${minuteString}"
                } else {
                    countdownMinutes.text = minutes.toString()
                }
                if (seconds < 10) {
                    val secondString = seconds.toString()
                    countdownSeconds.text = "0${secondString}"
                } else {
                    countdownSeconds.text = seconds.toString()
                }
                countdownMilis.text =
                    (millisUntilFinished - (millisUntilFinished / 1000 * 1000)).toString()
                val timeLeft: Int = (millisUntilFinished.toFloat() / RESTCOUNT * 100).toInt()
                progressBar.setProgress(timeLeft)
            }

            //Setting of what happens when timer finishes.
            override fun onFinish() {
                progressBar.setProgress(0)
                countdownMinutes.text = "00"
                countdownSeconds.text = "00"
                countdownMilis.text = "000"
                skipRest.visibility = View.INVISIBLE
                completeRest.visibility = View.VISIBLE
                completeEx.visibility = View.INVISIBLE
            }
        }
        /*End of timer configuration*/

        //Function to begin cycling between exercises.
        startExercise()

        /*On Click Listener for back button that double confirms user's intention to leave workout session.*/
        backButton.setOnClickListener {
            builder.setTitle("Exit Workout Confirmation")
                .setMessage("Leave current workout session?")
                .setCancelable(true)
                .setPositiveButton("Confirm") { _, it ->
                    val intent = Intent(this@StartExercise, FinalPage::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancel") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
        /*End of Back Button On Click Listener*/
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    /*This function will display the appropriate name, repetitions, and sets based on the
      id number found in the todo_database inputted.*/
    private fun startExercise() {
        lifecycleScope.launch(Dispatchers.IO) {
            val allTodos = viewModel.getListTodos()
            while (INDEX < allTodos.size) {
                currentExercise.text = allTodos[INDEX].name
                val reps = allTodos[INDEX].reps
                repetitions.setText("${reps} reps")
                val totalSet: Int = allTodos[INDEX].sets.toInt()
                var currentSet = 1
                val minutes = RESTCOUNT / 60000
                val seconds = ((RESTCOUNT - minutes * 60000) / 1000)
                if (minutes < 10) {
                    val minuteString = minutes.toString()
                    countdownMinutes.text = "0${minuteString}"
                } else {
                    countdownMinutes.text = minutes.toString()
                }
                if (seconds < 10) {
                    val secondString = seconds.toString()
                    countdownSeconds.text = "0${secondString}"
                } else {
                    countdownSeconds.text = seconds.toString()
                }
                countdownMilis.text = "000"

                /*While loop to loop through the sets of exercises*/
                while (currentSet <= totalSet) {
                    completeSet.text = "Complete Set"
                    setCounter.setText("${currentSet} of ${totalSet}")
                    completeSet.setOnClickListener {
                        timer.start()
                        completeRest.visibility = View.INVISIBLE
                        timerInstructions.visibility = View.INVISIBLE
                    }
                    if (currentSet == totalSet) {
                        completeRest.text = "Complete Exercise"
                    }
                    completeRest.setOnClickListener {
                        progressBar.setProgress(100)
                        timerInstructions.visibility = View.VISIBLE
                        countdownMilis.visibility = View.INVISIBLE
                        completeRest.visibility = View.INVISIBLE
                        completeSet.visibility = View.VISIBLE
                        currentSet += 1
                    }

                    /*Configure skipRest button on click listener*/
                    skipRest.setOnClickListener {
                        builder.setTitle("Confirmation")
                            .setMessage("Skip Current Rest Interval?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm") { _, it ->
                                timer.onTick(RESTCOUNT)
                                timer.cancel()
                                currentSet += 1
                                countdownMilis.text = "000"
                                skipRest.visibility = View.INVISIBLE
                                completeSet.visibility = View.VISIBLE
                                timerInstructions.visibility = View.VISIBLE
                            }
                            .setNegativeButton("Cancel") { dialogInterface, it ->
                                dialogInterface.cancel()
                            }
                            .show()
                    }
                    /*End of skipRest On Click Listener*/

                    if (currentSet > totalSet) {
                        if (INDEX < allTodos.size - 1) {
                            INDEX += 1
                            val intent = Intent(this@StartExercise, PrepExercise::class.java)
                            startActivity(intent)
                        } else {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val allTodos = viewModel.getListTodos()
                                for(elements in allTodos) {
                                    viewModel.deleteTodo(elements)
                                }
                            }
                            addHistory() // works
                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    "Congratulations! Your workout is completed and saved!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
//                            Toast.makeText(this@StartExercise, "Congratulations, workout has been saved!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@StartExercise, HistoryPageActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                /*End of exercise 'while' Loop*/
            }
        }
    }
    /*End of startExercise() function*/

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
        USER_NOTES = "ADDED"
    }
}