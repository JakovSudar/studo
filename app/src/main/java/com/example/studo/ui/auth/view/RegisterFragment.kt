package com.example.studo.ui.auth.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.studo.R
import com.example.studo.ui.auth.viewModel.AuthViewModel
import com.example.studo.ui.auth.viewModel.EMPLOYER
import com.example.studo.ui.auth.viewModel.STUDENT
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.et_password
import kotlinx.android.synthetic.main.fragment_register.et_username
import kotlinx.android.synthetic.main.fragment_register.layout
import kotlinx.android.synthetic.main.fragment_register.loginBtn
import kotlinx.android.synthetic.main.fragment_register.progressBar
import kotlinx.android.synthetic.main.fragment_register.registerBtn


class RegisterFragment() : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    var hasError: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpUi()
        setUpObserver()
    }

    private fun setUpObserver() {
        authViewModel.registerResponse().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    progressBar.visibility = View.GONE
                    tv_progress.visibility = View.GONE
                    Toast.makeText(context,"Registered", Toast.LENGTH_LONG).show()
                    layout.visibility = View.VISIBLE

                }
                Status.LOADING ->{
                    progressBar.visibility = View.VISIBLE
                    tv_progress.visibility = View.VISIBLE
                    layout.visibility = View.GONE
                }
                Status.ERROR->{
                    tv_progress.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    layout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpViewModel() {
        authViewModel = ViewModelProviders.of(this.activity!!).get(AuthViewModel::class.java)
    }

    private fun setUpUi() {
        tv_progress.visibility = View.GONE
        progressBar.visibility = View.GONE
        loginBtn.setOnClickListener{
            this.authViewModel.showLogin()
        }
        registerBtn.setOnClickListener {
            clearErrors()

            authViewModel.username = et_username.text.toString()
            authViewModel.password = et_password.text.toString()
            authViewModel.rpassword = et_rpassword.text.toString()
            authViewModel.email = et_email.text.toString()

            if(rb_student.isChecked){
                authViewModel.type = STUDENT
            }else{
                authViewModel.type = EMPLOYER
            }

            checkInputs()
            if(!hasError){
                authViewModel.register()
            }
        }
    }

    private fun checkInputs() {
            hasError = false
        if(!authViewModel.email.isEmailValid()){
            ly_email.error = "Unesite ispravan mail"
            hasError = true
        }
        if(authViewModel.username.isEmpty()){
            ly_username.error = "Obavezno polje!"
            hasError = true
        }
        if(authViewModel.password.length < 6){
            ly_password.error = "Minimalno 6 znakova"
            hasError = true
        }
        if(authViewModel.password != authViewModel.rpassword){
            ly_rpassword.error = "Lozinke se ne podudaraju"
            hasError = true
        }
    }

    private fun clearErrors() {
        ly_password.error= null
        ly_username.error= null
        ly_email.error= null
        ly_rpassword.error = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }
    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}
