package com.example.studo.ui.main.adapter

import android.app.PendingIntent.getActivity
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
import kotlinx.android.synthetic.main.job_item.view.*

class MainAdapter(
    private val jobs: ArrayList<Job>,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {


    class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)  {

        fun bind(job: Job, mainViewModel: MainViewModel){
            itemView.jobTitle.text = job.title
            itemView.jobWage.text = job.wage.toString() + " kn/h"
            itemView.description.text = job.description
            itemView.city.text = job.city
            itemView.setOnClickListener(){
                mainViewModel.getJob(job.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.job_item,parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return jobs.size
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(jobs[position],mainViewModel)

    fun addData(list: List<Job>){
        jobs.addAll(list)
    }
}