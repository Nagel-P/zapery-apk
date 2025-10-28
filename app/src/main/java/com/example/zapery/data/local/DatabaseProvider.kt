package com.example.zapery.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: ZaperyDatabase? = null

    fun get(context: Context): ZaperyDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                ZaperyDatabase::class.java,
                "zapery.db"
            ).fallbackToDestructiveMigration()
             .build()
             .also { INSTANCE = it }
        }
    }
}
