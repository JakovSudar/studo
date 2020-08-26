package com.example.studo.ui.main.view.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.studo.R
import com.example.studo.ui.main.viewModel.JobApplyViewModel
import kotlinx.android.synthetic.main.fragment_third_step.*

class ThirdStepFragment : Fragment() {

    lateinit var applyViewModel: JobApplyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        populateViews()
        setUpUi()
    }

    private fun setUpUi() {
        btn_apply.setOnClickListener {
            this.applyViewModel.step.postValue(4)
        }
        btn_back.setOnClickListener {
            this.applyViewModel.step.postValue(2)
        }
    }

    private fun populateViews() {
        tv_email.text = "Email: " + this.applyViewModel.email.value
        tv_aboutMe.text = "O meni: " + this.applyViewModel.aboutMe.value
        tv_phone.text = "Mobitel: " + this.applyViewModel.phone.value
    }

    private fun setUpViewModel() {
        this.applyViewModel = ViewModelProviders.of(this.activity!!).get(JobApplyViewModel::class.java)
    }

    companion object {
        fun newInstance() = ThirdStepFragment()
    }
}
