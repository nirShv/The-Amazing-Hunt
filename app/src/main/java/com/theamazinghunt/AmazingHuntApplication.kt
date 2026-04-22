package com.theamazinghunt

import android.app.Application
import com.theamazinghunt.di.AppContainer

class AmazingHuntApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
