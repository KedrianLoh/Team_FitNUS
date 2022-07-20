package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val EXTRA_MESSAGE = "com.example.recyclerviewexample.MESSAGE"
const val EXTRA_MESSAGE2 = "com.example.recycerviewexample.MESSAGE2"
var DETAIL_COUNT = 0
var DELETE_EXERCISE_MODE = 0

class SelectPageActivity : AppCompatActivity(), SelectPageAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var builder: AlertDialog.Builder

    val adapter = SelectPageAdapter(this)
//    private val allExercises = ArrayList<ExerciseDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_page)
        DELETE_EXERCISE_MODE = 0
        supportActionBar?.title = "Select Exercises"

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter // adapter for recycler view
        recyclerView.layoutManager = LinearLayoutManager(this) // defines the horizontal layout

        viewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        builder = AlertDialog.Builder(this)


        viewModel.allExerciseDetail.observe(this, Observer {
            adapter.updateData(it)
        })

    }

    override fun onBackPressed() {
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
    }

    fun insertItem(view: View) {
        val intent = Intent(this, InsertExercise::class.java)
        startActivity(intent)
        /*  exampleList.add(index, newItem)
            adapter.notifyItemInserted(index)*/
    }

    fun doneSelection(view: View) {
        val intent = Intent(this, FinalPage::class.java)
        startActivity(intent)
        /*  exampleList.removeAt(index)
            adapter.notifyItemRemoved(index)*/
    }

    override fun onItemClick(position: Int) {
        if (DELETE_EXERCISE_MODE == 1) {
            val currentItem = adapter.allExercises
            builder.setTitle("Delete Exercise")
                .setMessage("Delete ${currentItem[position].nameOfExercise}?")
                .setCancelable(true)
                .setPositiveButton("Delete") { _, it ->
                    viewModel.deleteExercise(currentItem[position])
                    Toast.makeText(
                        this,
                        "${currentItem[position].nameOfExercise} deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("Cancel") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        } else {
            val currentItemName: String = adapter.allExercises[position].nameOfExercise
            val currentItemMuscle: String = adapter.allExercises[position].muscleInvolved
            DETAIL_COUNT = 1
//            Toast.makeText(this, "$currentItemName clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DetailExerciseActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, currentItemName)
                putExtra(EXTRA_MESSAGE2, currentItemMuscle)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        val search = menu?.findItem(R.id.search_bar)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener((this))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val intent_logout = Intent(this, MainActivity2::class.java)
        when (item.itemId) {
            R.id.delete -> {
                if (DELETE_EXERCISE_MODE == 0) {
                    DELETE_EXERCISE_MODE = 1
                    supportActionBar?.title = "Delete Mode"
                    item.setIcon(R.drawable.ic_check)
                } else if (DELETE_EXERCISE_MODE == 1) {
                    DELETE_EXERCISE_MODE = 0
                    supportActionBar?.title = "Select Exercises"
                    item.setIcon(R.drawable.ic_delete)
                }
            }
//            R.id.logout -> startActivity(intent_logout)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.updateData(it)
            }
        }
    }


}
