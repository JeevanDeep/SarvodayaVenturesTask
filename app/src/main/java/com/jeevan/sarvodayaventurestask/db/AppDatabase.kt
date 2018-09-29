package com.jeevan.sarvodayaventurestask.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.jeevan.sarvodayaventurestask.model.SignUpModel

@Database(entities = [SignUpModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactsDAO(): ContactsDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "database-name").build()
            return INSTANCE as AppDatabase
        }
    }
}
