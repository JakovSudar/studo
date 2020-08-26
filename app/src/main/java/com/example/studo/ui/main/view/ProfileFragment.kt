package com.example.studo.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.data.model.User
import com.example.studo.helpers.PreferenceManager
import com.example.studo.helpers.PreferenceManager.Companion.DEF_VAL
import com.example.studo.ui.main.adapter.EMPLOYER_PROFILE
import com.example.studo.ui.main.adapter.MainAdapter
import com.example.studo.ui.main.adapter.STUDENT_PROFILE
import com.example.studo.ui.main.viewModel.MainViewModel
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.applied_students_dialog.*

import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment(val user: User) : Fragment() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var adapter : MainAdapter
    var jobs = mutableListOf<Job>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        fetchProfileJobs()
        setUpObserver()
        setUpUi()
    }


    private fun setUpUi() {
        btn_logOut.setOnClickListener {
            logOut()
        }

        tv_username.text = mainViewModel.getUser().name
        tv_type.text = "Tip profila: "+ mainViewModel.getUser().type
        tv_email.text = "Email: " +mainViewModel.getUser().email
    }

    private fun fetchProfileJobs() {
        if (mainViewModel.isStudent())
            mainViewModel.fetchAppliedJobs()
        else
            mainViewModel.fetchPostedJobs()
    }

    private fun logOut() {
        (activity as MainActivity).onLoggedOut()
        PreferenceManager().deleteUser()
        Log.d("PREFS", PreferenceManager().retriveUser().toString())
        mainViewModel.setUser(User(DEF_VAL,DEF_VAL,DEF_VAL,-1,-1,DEF_VAL))
        activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).remove(this).commit()
    }

    override fun onResume() {
        super.onResume()
        adapter.clearData()
        adapter.notifyDataSetChanged()
    }

    private fun setUpObserver() {
        mainViewModel.getProfileJobs().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    it.data?.let { jobs -> renderList(jobs)
                        this.jobs.addAll(jobs)
                        progressBar.visibility = View.GONE
                        tv_progress.visibility = View.GONE
                        if(jobs.size < 1){
                            if(mainViewModel.isStudent()){
                                tv_progress.text = "Niste prijavljeni na poslove"
                            }else
                                tv_progress.text = "Nemate objavljenih poslova"
                            tv_progress.visibility = View.VISIBLE
                        }
                    }
                }
                Status.LOADING ->{
                    progressBar.visibility = View.VISIBLE
                    tv_progress.text = "Dohvaćanje poslova"
                    tv_progress.visibility = View.VISIBLE
                }
                Status.ERROR->{
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        })

        mainViewModel.deletedJob().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    adapter.removeJob(it.data!!)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(
            this.activity!!
        ).get(MainViewModel::class.java)
    }

    private fun setUpRecycler() {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
        rv_profileJobs.addItemDecoration(itemDecorator)
        rv_profileJobs.layoutManager = LinearLayoutManager(context)

        adapter = MainAdapter(arrayListOf(),mainViewModel,if (mainViewModel.isStudent()) STUDENT_PROFILE else EMPLOYER_PROFILE)
        rv_profileJobs.adapter = adapter
    }

    private fun renderList(jobs: List<Job>) {
        adapter.addData(jobs)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(user: User) =ProfileFragment(user)
    }
}
