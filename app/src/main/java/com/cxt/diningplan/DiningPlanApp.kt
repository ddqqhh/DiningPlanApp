package com.cxt.diningplan

import android.app.Application

class DiningPlanApp : Application() {

    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}