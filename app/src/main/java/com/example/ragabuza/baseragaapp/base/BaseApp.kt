package com.example.ragabuza.baseragaapp.base

import android.app.Application
import android.content.Intent
import com.example.ragabuza.baseragaapp.BuildConfig
import com.google.gson.Gson
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

class BaseApp : Application() {

    lateinit var boxStore: BoxStore

    val globalVars = HashMap<String, Any>()

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this).build()
        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(boxStore).start(this)
        }
    }

}