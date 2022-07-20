package com.example.recyclerviewexample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.HistoryDatabase.HistoryDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//var  WORKOUT_HISTORY_INFO : List<TodoDetail>? = null
var WORKOUT_HISTORY_INFO: HistoryDetail? = null

class HistoryPageActivity : AppCompatActivity(), SelectPageAdapter.OnItemClickListener {

    private val adapter = HistoryPageAdapter(this)
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.neumorphism_history)
        supportActionBar?.hide()
//        supportActionBar?.title = "Create Workout!"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewDone)
        recyclerView.adapter = adapter // adapter for recycler view
        recyclerView.layoutManager = LinearLayoutManager(this) // defines the horizontal layout
        (recyclerView.layoutManager as LinearLayoutManager).setStackFromEnd(true);
        (recyclerView.layoutManager as LinearLayoutManager).setReverseLayout(true);

        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        viewModel.allHistoryDetail.observe(this, Observer {
            adapter.updateData(it)
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(position: Int) {
        val currentWorkoutDate: String = adapter.allHistory[position].date
//        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch(Dispatchers.IO) {
            WORKOUT_HISTORY_INFO = viewModel.getHistoryDetail(currentWorkoutDate)
        }
        val intent = Intent(this, WorkoutHistoryInfoActivity::class.java)
        startActivity(intent)
        adapter.notifyItemChanged(position)
    }

    fun createNewWorkout(view: View) {
        val intent = Intent(this, SelectPageActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_page_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent_logout = Intent(this, MainActivity2::class.java)
        when (item.itemId) {
            R.id.logout -> startActivity(intent_logout)
        }
        return super.onOptionsItemSelected(item)
    }

}