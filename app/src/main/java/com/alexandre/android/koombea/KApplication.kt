package com.alexandre.android.koombea

import android.app.Application
import android.content.Context
import com.couchbase.lite.CouchbaseLite
import com.alexandre.android.koombea.di.repoModule
import com.alexandre.android.koombea.di.viewModelModule
import org.koin.core.context.startKoin

class KApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: KApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(repoModule, viewModelModule))
        }

        val context: Context = applicationContext()
        CouchbaseLite.init(this)
    }
}