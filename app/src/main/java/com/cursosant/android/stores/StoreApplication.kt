package com.cursosant.android.stores

import android.app.Application
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class StoreApplication : Application() {
    companion object{
        lateinit var database: StoreDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration (1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE StoreEntity ADD COLUM photoUrl TEXT NOT NULL DEFAULT ''")
            }
        }

        database = Room.databaseBuiler (context: this,
        StoreDatabase :: class.java,
        name: "StoreDatabase")
        .addMigration(MIGRATION_1_2)
        .build()
    }
}