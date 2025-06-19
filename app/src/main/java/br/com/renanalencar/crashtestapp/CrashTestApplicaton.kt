package br.com.renanalencar.crashtestapp

import android.app.Application

class CrashTestApplication : Application() {
    companion object {
        lateinit var instance: CrashTestApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}