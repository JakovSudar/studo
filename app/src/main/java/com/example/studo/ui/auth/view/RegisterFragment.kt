package com.example.studo.ui.auth.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProviders

import com.example.studo.R
import com.example.studo.ui.auth.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class RegisterFragment() : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpViewModel() {
        authViewModel = ViewModelProviders.of(this.activity!!).get(AuthViewModel::class.java)
    }

    private fun setUpUi() {
        loginBtn.setOnClickListener{
            this.authViewModel.showLogin()
        }
        InputMethodManager.SHOW_IMPLICIT
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }
}
