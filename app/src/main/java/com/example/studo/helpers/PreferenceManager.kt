package com.example.studo.helpers

import android.content.Context
import android.util.Log
import com.example.studo.data.model.User

class PreferenceManager {
    companion object {
        const val PREFS_FILE = "StudoPreferences"
        const val PREFS_KEY_TOKEN = "Token"
        const val PREFS_KEY_USERNAME = "Username"
        const val PREFS_KEY_TYPE = "Type"
        const val PREFS_KEY_ID = "Id"
        const val PREFS_KEY_EMAIL = "email"
        const val DEF_VAL = "unknown"
    }
    fun saveUser(user: User){
        Log.d("Preference Manager", user.toString())
        saveToken(user.accessToken)
        saveId(user.id.toString())
        saveType(user.type)
        saveUsername(user.name)
        saveEmail(user.email)

    }
    fun retriveUser(): User{
        val user = User(getEmail(),getUsername(),getType(),0,getId().toInt(),getToken())
        return user
    }
    fun saveToken(accesToken: String) {
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_TOKEN, accesToken)
        editor.apply()
    }
    fun saveUsername(username: String){
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_USERNAME, username)
        editor.apply()
    }

    fun saveType(type: String){
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_TYPE, type)
        editor.apply()
    }
    fun saveEmail(type: String){
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_EMAIL, type)
        editor.apply()
    }

    fun saveId(id: String){
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_ID, id)
        editor.apply()
    }
    fun getUsername(): String {
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_KEY_USERNAME, DEF_VAL)!!
    }
    fun getEmail(): String {
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_KEY_EMAIL, DEF_VAL)!!
    }
    fun getToken(): String{
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_KEY_TOKEN, DEF_VAL)!!
    }
    fun getType(): String{
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_KEY_TYPE, DEF_VAL)!!
    }
    fun getId(): String{
        val sharedPreferences = StudoApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_KEY_ID, DEF_VAL)!!
    }
}