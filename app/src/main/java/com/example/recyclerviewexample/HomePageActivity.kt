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
import org.w3c.dom.Text

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

        convertLine2Titles()
        displayHistoryCountAndWipeTodo()
        // Resetting the global variables: Workout Notes and Type Of Training
        USER_NOTES = null
        TYPE_TRAINING = 0

//        Toast.makeText(this, "changed!", Toast.LENGTH_SHORT).show()
    }

    private fun displayHistoryCountAndWipeTodo() {
        lifecycleScope.launch(Dispatchers.IO) {
            val allTodos = todoViewModel.getListTodos()
            for (elements in allTodos) {
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

    private fun convertLine2Titles() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listOfHistory = historyViewModel.getListHistory()
            var punchCount = 0
            var weightCount = 0
            var runCount = 0
            var yogaCount = 0
            for (elements in listOfHistory) {
                when (elements.type) {
                    0 -> punchCount += 1
                    1 -> weightCount += 1
                    2 -> runCount += 1
                    3 -> yogaCount += 1
                }
            }
            val punchLine2 = findViewById<TextView>(R.id.punchLine2)
            val weightLine2 = findViewById<TextView>(R.id.weightLine2)
            val runLine2 = findViewById<TextView>(R.id.runLine2)
            val yogaLine2 = findViewById<TextView>(R.id.yogaLine2)

            if (punchCount == 1) {
                punchLine2.setText("${punchCount} training done!")
            } else if (punchCount == 0) {
                punchLine2.setText("Try This!")
            } else {
                punchLine2.setText("${punchCount} trainings done!")
            }

            if (weightCount == 1) {
                weightLine2.setText("${weightCount} training done!")
            } else if (weightCount == 0) {
                weightLine2.setText("Try This!")
            } else {
                weightLine2.setText("${weightCount} trainings done!")
            }

            if (runCount == 1) {
                runLine2.setText("${runCount} training done!")
            } else if (runCount == 0) {
                runLine2.setText("Try This!")
            } else {
                runLine2.setText("${runCount} trainings done!")
            }

            if (yogaCount == 1) {
                yogaLine2.setText("${yogaCount} training done!")
            } else if (yogaCount == 0) {
                yogaLine2.setText("Try This!")
            } else {
                yogaLine2.setText("${yogaCount} trainings done!")
            }
        }
    }

    fun iconHome(view: View) {
        setContentView(R.layout.neumorphism)
        displayHistoryCountAndWipeTodo()
        convertLine2Titles()
    }

    fun iconProfile(view: View) {
        setContentView(R.layout.neumorphism_profile)
    }

    fun iconChart(view: View) {
        setContentView(R.layout.neumorphism_chart)
        lifecycleScope.launch (Dispatchers.IO) {
        val listOfHistory = historyViewModel.getListHistory()
        // for punch details
            val punchExercises = findViewById<TextView>(R.id.punchTotalExercise)
            val punchFull = findViewById<TextView>(R.id.punchFullBody)
            val punchUpper = findViewById<TextView>(R.id.punchUpperBody)
            val punchLower = findViewById<TextView>(R.id.punchLowerBody)
            var punchExercisesCount = 0
            var punchFullCount = 0
            var punchUpperCount = 0
            var punchLowerCount = 0
        // for weight details
            val weightExercises = findViewById<TextView>(R.id.weightTotalExercise)
            val weightSets = findViewById<TextView>(R.id.weightTotalSets)
            val weightReps = findViewById<TextView>(R.id.weightTotalReps)
            var weightExercisesCount = 0
            var weightSetsCount = 0
            var heaviestLiftName: String = "heavy"
            var heaviestLiftNumber = 0
        // for run details
            val runDistance = findViewById<TextView>(R.id.runTotalDistance)
            val runDuration = findViewById<TextView>(R.id.runTotalDuration)
            var runDistanceCount = 0.0
            var runDurationCount = 0
        // for yoga details
            val yogaExercises = findViewById<TextView>(R.id.yogaTotalExercise)
            val yogaFull = findViewById<TextView>(R.id.yogaFullbody)
            val yogaUpper = findViewById<TextView>(R.id.yogaUpperBody)
            val yogaLower = findViewById<TextView>(R.id.yogaLowerBody)
            var yogaExercisesCount = 0
            var yogaFullCount = 0
            var yogaUpperCount = 0
            var yogaLowerCount = 0

            for(elements in listOfHistory) {
                when (elements.type) {
                    0 -> {
                        punchExercisesCount += elements.arrayList.size
                        for (i in 0 until elements.arrayList.size - 1) {
                            when (elements.arrayList[i].muscle) {
                                "Full body" -> { punchFullCount += 1 }
                                "Upper body" -> { punchUpperCount += 1 }
                                "Lower body" -> { punchLowerCount += 1 }
                            }
                        }
                        punchExercises.setText("Total Exercise : ${punchExercisesCount}")
                        punchFull.setText("Total Full Body : ${punchFullCount}")
                        punchUpper.setText("Total Upper Body : ${punchUpperCount}")
                        punchLower.setText("Total Lower Body : ${punchLowerCount}")
                    }

                    1 -> {
                        weightExercisesCount += elements.arrayList.size
                        for (i in 0 until elements.arrayList.size) {
                          weightSetsCount += elements.arrayList[i].sets.toInt()
                            if (elements.arrayList[i].weight.toInt() > heaviestLiftNumber) {
                                heaviestLiftNumber = elements.arrayList[i].weight.toInt()
                                heaviestLiftName = elements.arrayList[i].name
                            }
                        }
                        weightExercises.setText("Total exercises : ${weightExercisesCount}")
                        weightSets.setText("Total Sets : ${weightSetsCount}")
                        weightReps.setText("Heaviest Lift :\n${heaviestLiftName} - ${heaviestLiftNumber}kg")
                    }

                    2 -> {
                        runDistanceCount += elements.arrayList[0].weight.toDouble()
                        runDurationCount += elements.arrayList[0].time.toInt()
                        runDistance.setText("Total Distance : ${runDistanceCount}km")
                        runDuration.setText("Total Duration : ${runDurationCount}mins")
                    }

                    3 -> {
                        yogaExercisesCount += elements.arrayList.size
                        for (i in 0 until elements.arrayList.size) {
                            when (elements.arrayList[i].muscle) {
                                "Full body" -> { yogaFullCount += 1 }
                                "Upper body" -> { yogaUpperCount += 1 }
                                "Lower body" -> { yogaLowerCount += 1 }
                            }
                        }
                        yogaExercises.setText("Total Exercise : ${yogaExercisesCount}")
                        yogaFull.setText("Total Full Body : ${yogaFullCount}")
                        yogaUpper.setText("Total Upper Body : ${yogaUpperCount}")
                        yogaLower.setText("Total Lower Body : ${yogaLowerCount}")
                    }
                }
            }
        }
    }

    fun viewHistories(view: View) {
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
