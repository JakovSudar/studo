package com.example.studo.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.job_details_dialog.*
import kotlinx.android.synthetic.main.job_item.city

class JobDetailsDialog(private val job: Job): DialogFragment() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.job_details_dialog,container,false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setUpUi()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(
            this.activity!!
        ).get(MainViewModel::class.java)
    }

    private fun setUpUi() {
        btnApply.setOnClickListener{
            applyOnJob(job)
        }
        btnClose.setOnClickListener{
            dismiss()
        }
    }

    private fun applyOnJob( job :Job) {
        mainViewModel.applyToJob(job)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        opis.text = job.description
        satnica.text = job.wage.toString() + " kn/h"
        city.text = job.city
        zahtjevi.text = job.requirements
        jobTitleDialog.text = job.title
        employer.text = job.employer
    }



}