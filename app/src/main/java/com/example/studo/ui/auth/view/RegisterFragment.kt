package com.example.studo.ui.auth.view

import android.os.Bundle
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
                    Toast.makeText(context,"Registered", Toast.LENGTH_LONG).show()
                    layout.visibility = View.VISIBLE
                }
                Status.LOADING ->{
                    progressBar.visibility = View.VISIBLE
                    layout.visibility = View.GONE
                }
                Status.ERROR->{
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
        progressBar.visibility = View.GONE
        loginBtn.setOnClickListener{
            this.authViewModel.showLogin()
        }
        registerBtn.setOnClickListener {
            authViewModel.username = et_username.text.toString()
            authViewModel.password = et_password.text.toString()
            authViewModel.rpassword = et_rpassword.text.toString()
            authViewModel.email = et_email.text.toString()

            authViewModel.username = "username@gmail.com"
            authViewModel.password = "jakov123j"
            authViewModel.rpassword = "jakov123j"
            authViewModel.email = "email@gmail.com"

            if(rb_student.isChecked){
                authViewModel.type = STUDENT
            }else{
                authViewModel.type = EMPLOYER
            }

            authViewModel.register()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }
}
