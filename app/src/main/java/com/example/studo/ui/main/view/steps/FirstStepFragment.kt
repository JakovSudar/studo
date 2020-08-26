package com.example.studo.ui.main.view.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.studo.R
import com.example.studo.ui.main.viewModel.JobApplyViewModel
import kotlinx.android.synthetic.main.fragment_first_step.*


class FirstStepFragment : Fragment() {
    lateinit var applyViewModel: JobApplyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpViewModel() {
        this.applyViewModel = ViewModelProviders.of(this.activity!!).get(JobApplyViewModel::class.java)
    }

    private fun setUpUi() {
        if(!applyViewModel.aboutMe.value.isNullOrBlank()){
            et_aboutMe.setText(applyViewModel.aboutMe.value)
        }
        btn_next.setOnClickListener {
            ly_aboutMe.error = null
            if(checkInput()){
                this.applyViewModel.aboutMe.postValue(et_aboutMe.text.toString())
                this.applyViewModel.step.postValue(2)
            }
        }
    }

    private fun checkInput() : Boolean{
        if(et_aboutMe.text.isNullOrBlank()){
            ly_aboutMe.error = "Napišite nešto o sebi!"
            return false
        }
        return true
    }


    companion object {
        fun newInstance() =  FirstStepFragment()
    }
}
