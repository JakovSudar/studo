package com.example.studo.ui.auth.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studo.data.model.User
import com.example.studo.data.api.Networking
import com.example.studo.data.model.response.LoginResponse
import com.example.studo.data.model.request.UserDataRequest
import com.example.studo.data.model.response.RegisterResponse
import com.example.studo.helpers.PreferenceManager
import com.example.studo.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val LOGIN = "LOGIN"
const val REGISTER = "REGISTER"

const val STUDENT = "student"
const val EMPLOYER = "employer"

class AuthViewModel: ViewModel() {
    var loggedUser = MutableLiveData<Resource<User>>()
    var loginNavigation = MutableLiveData<String>()
    var loginResponse = MutableLiveData<Resource<LoginResponse>>()
    var registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    var username: String = ""
    var password: String = ""
    var rpassword: String= "a"
    var email: String = ""
    var type: String =""
    var id: Int = -1

    fun showLogin(){
        this.loginNavigation.postValue(LOGIN)
    }
    fun showRegister(){
        this.loginNavigation.postValue(REGISTER)
    }

    fun login(){
        val userData = UserDataRequest(email,password,null,null, null)
        loggedUser.postValue(Resource.loading(null))
        Log.d("Login credentials", userData.toString())
        Networking.apiService.login(userData).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResponse.postValue(Resource.error("Network problem",null))
                    Log.v("retrofit", t.message)
                }
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(!response.isSuccessful){
                        loggedUser.postValue(Resource.error("Wrong credentials",null))
                        return
                    }
                    var user = User(email,username,type,0,id,response.body()!!.accessToken)
                    if(user.type == ""){
                        Networking.apiService.whoIsLogged("Bearer " + user.accessToken).enqueue(object :
                            Callback<User>{
                            override fun onFailure(call: Call<User>, t: Throwable) {
                                loggedUser.postValue(Resource.error(t.message.toString(),null))
                            }
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                user.name = response.body()!!.name
                                user.type = response.body()!!.type
                                user.id = response.body()!!.id
                                loggedUser.postValue(Resource.success(user))
                            }
                        })
                    }else
                        loggedUser.postValue(Resource.success(user))
                }
            }
        )
    }



    fun register(){
        val userData = UserDataRequest(email,password,rpassword,username,type)
        registerResponse.postValue(Resource.loading(null))
        Log.d("Register credentials", userData.toString())

        Networking.apiService.register(userData).enqueue(
            object : Callback<RegisterResponse>{
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registerResponse.postValue(Resource.error("Network problem",null))
                    Log.v("retrofit", t.message)
                }

                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if(!response.isSuccessful){
                        registerResponse.postValue(Resource.error("Wrong credentials",null))
                        return
                    }
                    registerResponse.postValue(Resource.success(response.body()))
                    id = response.body()!!.user.id
                }
            }
        )
    }



    fun loginResponse(): LiveData<Resource<LoginResponse>>{
        return loginResponse
    }

    fun registerResponse():LiveData<Resource<RegisterResponse>>{
        return registerResponse
    }
}