package com.example.studo.ui.main.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.data.model.User
import com.example.studo.helpers.PreferenceManager
import com.example.studo.ui.auth.view.AuthActivity
import com.example.studo.ui.main.adapter.HOMEPAGE
import com.example.studo.ui.main.adapter.MainAdapter
import com.example.studo.ui.main.viewModel.MainViewModel
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.applied_students_dialog.*

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
        getLoggedUser()
        setUpUi()
        setUpObserver()
    }

    private fun getLoggedUser() {
        val user : User =  PreferenceManager().retriveUser()
        mainViewModel.setUser(user)
        Log.d("Logged User", user.toString())
    }

    private fun setUpUi() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf(),mainViewModel, HOMEPAGE)
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.main_divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
        recyclerView.adapter = adapter

        btn_Profile.setOnClickListener{
            getLoggedUser()
            Log.d("MAIN", mainViewModel.getUser().toString())
            if(mainViewModel.hasToken()){
                var fm = supportFragmentManager
                var profileFragment = ProfileFragment.newInstance(PreferenceManager().retriveUser())
                fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
                    replace(R.id.appLayout,profileFragment,"profileFragmenr").
                    addToBackStack(null).
                    commit()
            }else{
                startAuthActivity()
            }
        }
        showAddBtn()
        btn_addJob?.setOnClickListener {
            openNewJobDialog()
        }
    }

    private fun startAuthActivity() {
        val authIntent = Intent(this, AuthActivity::class.java)
        startActivityForResult(authIntent,REQUEST_AUTH )
    }

    private fun showAddBtn() {
        if(mainViewModel.isStudent() || !mainViewModel.hasToken()) run {
            btn_addJob.visibility = View.GONE
        }else
            btn_addJob.visibility = View.VISIBLE
    }

    private fun openNewJobDialog() {
        var fm = supportFragmentManager
        var dialogFragment = NewJobDialog()
        dialogFragment.show(fm,"Dialog fragment")
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

        mainViewModel.jobToApply.observe(this, Observer {
            if(mainViewModel.hasToken()){
                if(mainViewModel.isStudent()){
                    var fm = supportFragmentManager
                    var dialogFragment = JobApplyDialog(it)
                    dialogFragment.show(fm,"JobApplicationDialog")
                }else{
                    Toast.makeText(this,"Niste student!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Prijavite se prije nastavka!", Toast.LENGTH_SHORT).show()
                startAuthActivity()
            }
        })

        mainViewModel.jobApplicationsDisplay.observe(this, Observer {
            var fm = supportFragmentManager
            var appliedStudentsDialog = AppliedStudentsDialog(it)
            appliedStudentsDialog.show(fm,"JobApplicationDialog")
        })

        mainViewModel.postedJob.observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    progressBar.visibility = View.GONE
                    Toast.makeText(this,"Posao je objavljen!", Toast.LENGTH_SHORT).show()
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
    }

    fun onLoggedOut(){
        btn_addJob.visibility = View.GONE
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
                    getLoggedUser()
                }else
                Log.d("ActivityResult", "Canceled")
                showAddBtn()
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}
