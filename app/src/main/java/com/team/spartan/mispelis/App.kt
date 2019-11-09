package com.team.spartan.mispelis

import android.app.Application
import com.team.spartan.mispelis.data.database.MovieDatabase

lateinit var database: MovieDatabase

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        database = MovieDatabase.getInstance(this)
        INSTANCE = this
    }
}