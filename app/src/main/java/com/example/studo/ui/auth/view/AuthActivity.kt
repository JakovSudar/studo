package com.example.studo.ui.auth.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studo.R
import com.example.studo.ui.auth.viewModel.AuthViewModel
import com.example.studo.ui.auth.viewModel.LOGIN
import com.example.studo.ui.auth.viewModel.REGISTER
import com.example.studo.ui.main.view.MainActivity
import com.example.studo.utils.Status



class AuthActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setUpViewModel()
        setUpUi()
        setUpObservers()
    }

    private fun setUpObservers() {
        this.authViewModel.loginNavigation.observe(this, Observer {
            when(it){
                LOGIN ->{
                    showLogin()
                }
                REGISTER ->{
                    showRegistration()
                }
            }
        })

        this.authViewModel.registerResponse().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    showLogin()
                    authViewModel.login()
                }
            }
        })

        this.authViewModel.loginResponse().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                   val loginResult = Intent()
                    loginResult.putExtra(MainActivity.KEY_LOGIN,true)
                    setResult(Activity.RESULT_OK, loginResult)
                    finish()
                }
            }
        })
    }

    private fun setUpViewModel() {
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    private fun setUpUi() {
       showLogin()
    }

    private fun showLogin(){
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
        replace(R.id.fragmentContainer,
        LoginFragment.newInstance(),"loginFragment").commit()
    }
    private fun showRegistration(){
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).
            replace(R.id.fragmentContainer,
            RegisterFragment.newInstance(),"registerFragment").commit()
    }
}
