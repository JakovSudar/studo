package com.example.studo.helpers

import android.app.Application
import android.content.Context

class StudoApplication: Application() {
    companion object {
        lateinit var ApplicationContext: Context
            private set
    }
    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }

}