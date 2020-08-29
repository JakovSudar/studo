package com.example.studo.ui.main.adapter

import android.app.PendingIntent.getActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.ui.base.ViewModelFactory
import com.example.studo.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.employer_job_item.view.*
import kotlinx.android.synthetic.main.job_item.view.*
import kotlinx.android.synthetic.main.job_item.view.city
import kotlinx.android.synthetic.main.job_item.view.description
import kotlinx.android.synthetic.main.job_item.view.jobTitle
import kotlinx.android.synthetic.main.job_item.view.jobWage
import kotlinx.android.synthetic.main.student_job_item.view.*

const val STUDENT_PROFILE = "student"
const val EMPLOYER_PROFILE = "employer"
const val HOMEPAGE = "homepage"

class MainAdapter(
    private val jobs: ArrayList<Job>,
    private val mainViewModel: MainViewModel,
    private val type: String
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {


    class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)  {

        fun bind(job: Job, mainViewModel: MainViewModel,type: String){
            itemView.jobTitle.text = job.title
            itemView.jobWage.text = "Satnica: " + job.wage.toString() + " kn/h"
            itemView.description.text =job.description
            itemView.city.text = job.city
            itemView.tv_applicants?.text = job.applications!!.size.toString()
            itemView.setOnClickListener(){
                when(type){
                    EMPLOYER_PROFILE->{
                        mainViewModel.showJobApplications(job)
                    }
                    STUDENT_PROFILE ->{
                        mainViewModel.getJob(job)
                    }
                    HOMEPAGE->{
                        mainViewModel.getJob(job)
                    }
                }
            }
            itemView.btn_cancelJob?.setOnClickListener {
                mainViewModel.deleteJob(job)
                Log.d("MVM delete", job.toString())
            }
            itemView.btn_applyToJob?.setOnClickListener {
                mainViewModel.applyToJob(job)
            }
            itemView.btn_cancelApply?.setOnClickListener {
                mainViewModel.cancelApply(job)
                Log.d("CANCEL APPLY", job.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        when(type){
            STUDENT_PROFILE -> {
                return DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.student_job_item,parent,false
                    )
                )
            }
            EMPLOYER_PROFILE -> {
                return DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.employer_job_item,parent,false
                    )
                )
            }
            else -> {
                return DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.job_item,parent,false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(jobs[position],mainViewModel,type)

    fun addData(list: List<Job>){
        jobs.addAll(list)
    }
    fun removeJob(job: Job){
        job.applications = arrayListOf()
        Log.d("JAKOV removing", jobs.remove(job).toString())
    }
    fun clearData(){
        jobs.clear()
    }

}