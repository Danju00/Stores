package com.cursosant.android.stores

import android.app.Application

class StoreApplication : Application() {
    companion object{
        lateinit var database: StoreDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuiler (context: this,
        StoreDatabase :: class.java,
        name: "StoreDatabase")
        .build()
    }
}