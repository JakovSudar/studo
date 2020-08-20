package com.example.studo.ui.auth.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
import com.example.studo.utils.Status
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.et_email
import kotlinx.android.synthetic.main.fragment_login.et_password
import kotlinx.android.synthetic.main.fragment_login.layout
import kotlinx.android.synthetic.main.fragment_login.loginBtn
import kotlinx.android.synthetic.main.fragment_login.progressBar
import kotlinx.android.synthetic.main.fragment_login.registerBtn
import kotlinx.android.synthetic.main.fragment_login.tv_progress



class LoginFragment() : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private var hasError: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.GONE
        tv_progress.visibility = View.GONE
        setUpViewModel()
        setUpUi()
        setUpObserver()
    }

    private fun setUpObserver() {
        authViewModel.loginResponse().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    tv_progress.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    Toast.makeText(context,"Logged in!", Toast.LENGTH_LONG).show()
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
        this.authViewModel = ViewModelProviders.of(this.activity!!).get(AuthViewModel::class.java)
    }

    private fun setUpUi() {

        registerBtn.setOnClickListener{
            this.authViewModel.showRegister()
        }
        loginBtn.setOnClickListener{
            clearErrors()
            processLogin()
        }

    }

    private fun processLogin() {
        hideKeyboard()
        authViewModel.password = et_password.text.toString()
        authViewModel.email = (et_email.text.toString())
        checkInputs()
        if(!hasError){
            authViewModel.login()
        }

    }


    private fun hideKeyboard(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun checkInputs() {
        hasError = false
        if(!authViewModel.email.isEmailValid()){
            ly_email.error = "Unesite ispravan mail"
            hasError = true
        }
        if(authViewModel.password.length < 6){
            ly_password.error = "Minimalno 6 znakova"
            hasError = true
        }
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun clearErrors() {
        ly_password.error= null
        ly_email.error= null

    }

    companion object {
        @JvmStatic
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
