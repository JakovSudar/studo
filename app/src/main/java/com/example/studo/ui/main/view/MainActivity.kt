package com.example.studo.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studo.R
import com.example.studo.data.api.ApiHelper
import com.example.studo.data.api.ApiServiceImpl
import com.example.studo.data.model.Job
import com.example.studo.ui.base.ViewModelFactory
import com.example.studo.ui.main.adapter.MainAdapter
import com.example.studo.ui.main.viewModel.MainViewModel
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        setUpViewModel()
        setUpUi()
        setUpObserver()
    }

    private fun setUpUi() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf(),mainViewModel)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setUpObserver() {
        mainViewModel.getJobs().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    progressBar.visibility = View.GONE
                    it.data?.let { jobs -> renderList(jobs) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING ->{
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR->{
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        mainViewModel.job.observe(this, Observer {
            var fm = supportFragmentManager
            var dialogFragment = JobDetailsDialog(it)
            dialogFragment.show(fm,"Dialog fragment")
        })
    }

    private fun setUpViewModel() {
       mainViewModel = ViewModelProviders.of(
           this,
           ViewModelFactory(ApiHelper(ApiServiceImpl()))
       ).get(MainViewModel::class.java)
    }

    private fun renderList(jobs: List<Job>) {
        adapter.addData(jobs)
        adapter.notifyDataSetChanged()
    }
}
