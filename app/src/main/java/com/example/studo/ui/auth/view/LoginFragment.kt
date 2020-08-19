package com.example.studo.ui.auth.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProviders
import com.example.studo.Networking
import com.example.studo.R
import com.example.studo.data.model.AuthResponse
import com.example.studo.data.model.request.UserDataRequest
import com.example.studo.ui.auth.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment() : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.GONE
        setUpViewModel()
        setUpUi()
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

        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)

        val username = "dolly.denesik@example.com"
        val password = "secret"

        val userData = UserDataRequest(username,password)
       /* val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.137.1:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val authApi = retrofit.create(ApiService::class.java)

        val call : Call<AuthResponse> = authApi.login(userData)

        */
        progressBar.visibility = View.VISIBLE
        layout.visibility = View.GONE
        Networking.apiService.login(userData).enqueue(
            object : Callback<AuthResponse>{
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.v("retrofit", t.message)
                }

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    progressBar.visibility = View.GONE
                    layout.visibility = View.VISIBLE
                    if(!response.isSuccessful){
                        Log.v("retrofit", "Bad request")
                        return
                    }
                    val loginResponse = response.body()
                    Log.v("retrofit", loginResponse?.accessToken)

                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
