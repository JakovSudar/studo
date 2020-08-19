package com.example.studo.ui.auth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {

     var loginNavigation = MutableLiveData<String>()

    fun showLogin(){
        this.loginNavigation.postValue("LOGIN")
    }
    fun showRegister(){
        this.loginNavigation.postValue("REGISTER")
    }

    private fun login(username: String, password: String){

    }
}