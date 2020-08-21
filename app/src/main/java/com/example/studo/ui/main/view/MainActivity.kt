package com.example.studo.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studo.R
import com.example.studo.data.api.ApiHelper
import com.example.studo.data.model.Job
import com.example.studo.data.model.User
import com.example.studo.helpers.Networking
import com.example.studo.helpers.PreferenceManager
import com.example.studo.ui.auth.view.AuthActivity
import com.example.studo.ui.base.ViewModelFactory
import com.example.studo.ui.main.adapter.MainAdapter
import com.example.studo.ui.main.viewModel.MainViewModel
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter : MainAdapter

    companion object{
        const val REQUEST_AUTH = 10
        const val KEY_LOGIN = "loginResult"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        btn_Login.setOnClickListener{
            val authIntent = Intent(this, AuthActivity::class.java)
            startActivityForResult(authIntent,REQUEST_AUTH )
        }
        btn_addJob.setOnClickListener {
            openNewJobDialog()
        }
    }

    private fun openNewJobDialog() {
        TODO("Not yet implemented")
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
           this
       ).get(MainViewModel::class.java)
    }

    private fun renderList(jobs: List<Job>) {
        adapter.addData(jobs)
        adapter.notifyDataSetChanged()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_AUTH->{
                    if(resultCode == Activity.RESULT_OK){
                        Log.d("Activity result", "Logged")
                        val user = PreferenceManager().retriveUser()


                    }else
                        Log.d("Activity result", "Canceled")
                 }
        }
    }

}
