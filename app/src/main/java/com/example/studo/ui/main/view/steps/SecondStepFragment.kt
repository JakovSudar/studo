package com.example.studo.ui.main.view.steps

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.studo.R
import com.example.studo.ui.main.viewModel.JobApplyViewModel
import kotlinx.android.synthetic.main.fragment_second_step.*


class SecondStepFragment : Fragment() {
    lateinit var applyViewModel: JobApplyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_step, container, false)
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
        if(!applyViewModel.email.value.isNullOrBlank()){
            et_email.setText(applyViewModel.email.value)
        }
        if(!applyViewModel.phone.value.isNullOrBlank()){
            et_phone.setText(applyViewModel.phone.value)
        }
        btn_back.setOnClickListener {
            this.applyViewModel.step.postValue(1)
        }
        btn_next.setOnClickListener {
            ly_email.error = null
            ly_phone.error = null
            if(checkInputs()){
                this.applyViewModel.email.postValue(et_email.text.toString())
                this.applyViewModel.phone.postValue(et_phone.text.toString())
                this.applyViewModel.step.postValue(3)
            }
        }
    }

    private fun checkInputs(): Boolean {
        if(et_email.text.isNullOrBlank() || !et_email.text.toString().isEmailValid()){
            ly_email.error = "Unesite email!"
            return false
        }
        if(et_phone.text.isNullOrBlank()){
            ly_phone.error = "Unesite broj!"
            return false
        }
        return true
    }

    companion object {

        fun newInstance() = SecondStepFragment()
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}
