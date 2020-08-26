package com.example.studo.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.ui.main.view.steps.FirstStepFragment
import com.example.studo.ui.main.view.steps.SecondStepFragment
import com.example.studo.ui.main.view.steps.ThirdStepFragment
import com.example.studo.ui.main.viewModel.JobApplyViewModel
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.job_apply_dialog.*
import java.lang.Exception

class JobApplyDialog(val job: Job) : DialogFragment() {

    lateinit var applyViewModel: JobApplyViewModel
    val checkedMark = "\u2713"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.job_apply_dialog,container,false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpObserver()
        setUpView()
        progressBar.visibility = View.GONE
        tv_progress.visibility = View.GONE
    }

    private fun setUpObserver() {
        this.applyViewModel.step.observe(this, Observer {
            when(it){
                1-> goToFirstStep()
                2-> goToSecondStep()
                3-> goToThirdStep()
                4-> applyToJob()
            }
        })

        this.applyViewModel.applicationResponse.observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    progressBar.visibility = View.GONE
                    tv_progress.visibility = View.GONE
                    Toast.makeText(context,"Prijavljeni ste na posao", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                Status.LOADING->{
                    progressBar.visibility = View.VISIBLE
                    tv_progress.visibility = View.VISIBLE
                }
                Status.ERROR->{
                    progressBar.visibility = View.GONE
                    tv_progress.visibility = View.GONE
                }
            }
        })
    }

    private fun applyToJob() {
        applyViewModel.jobId =job.id
        applyViewModel.applyToJob()

    }

    private fun goToThirdStep() {
        step1.text = checkedMark
        step1.setBackgroundResource(R.drawable.step_done)
        step2.text = checkedMark
        step2.setBackgroundResource(R.drawable.step_done)
        step3.setBackgroundResource(R.drawable.step_current)
        var fm = this.childFragmentManager
        var thirdStepFragment = ThirdStepFragment.newInstance()
        fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
        replace(R.id.frameLayout,thirdStepFragment,"step1").
        commit()

    }

    private fun goToFirstStep() {
        step2.setBackgroundResource(R.drawable.step_todo)
        step2.text = 2.toString()
        step1.setBackgroundResource(R.drawable.step_current)
        step1.text = 1.toString()
        var fm = this.childFragmentManager
        var firstStepFragment = FirstStepFragment.newInstance()
        fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
        replace(R.id.frameLayout,firstStepFragment,"step1").
        commit()
    }

    private fun goToSecondStep() {
        step1.text = checkedMark
        step1.setBackgroundResource(R.drawable.step_done)
        step2.setBackgroundResource(R.drawable.step_current)
        step3.setBackgroundResource(R.drawable.step_todo)

        var fm = this.childFragmentManager
        var secondStepFragment = SecondStepFragment.newInstance()
        fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
        replace(R.id.frameLayout,secondStepFragment,"step1").
        commit()
    }

    private fun setUpView() {
        var fm = this.childFragmentManager
        var firstStepFragment = FirstStepFragment.newInstance()
        fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
        add(R.id.frameLayout,firstStepFragment,"step1").
        commit()
    }

    private fun setUpViewModel() {
        this.applyViewModel = ViewModelProviders.of(this.activity!!).get(JobApplyViewModel::class.java)
    }
}