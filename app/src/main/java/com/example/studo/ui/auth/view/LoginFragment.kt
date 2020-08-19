package com.example.studo.ui.auth.view

import android.content.Context
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_login.progressBar


class LoginFragment() : Fragment() {
    private lateinit var authViewModel: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.GONE
        setUpViewModel()
        setUpUi()
        setUpObserver()
    }

    private fun setUpObserver() {
        authViewModel.loginResponse().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    progressBar.visibility = View.GONE
                    Toast.makeText(context,"Logged in!", Toast.LENGTH_LONG).show()
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
        this.authViewModel = ViewModelProviders.of(this.activity!!).get(AuthViewModel::class.java)
    }

    private fun setUpUi() {

        registerBtn.setOnClickListener{
            this.authViewModel.showRegister()
        }
        loginBtn.setOnClickListener{
            processLogin()
        }

    }

    private fun processLogin() {
        hideKeyboard()
        authViewModel.password = et_password.text.toString()
        authViewModel.email = (et_email.text.toString())
        authViewModel.login()
    }


    private fun hideKeyboard(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
