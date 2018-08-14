package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class BaseActivity : NoPresenterActivity() {

    abstract val presenter: BasePresenter<out BaseActivity>

    lateinit var shared: SharedPreferences

    private fun setupPresenter() {
        shared = getSharedPreferences(
                packageName, Context.MODE_PRIVATE)
        presenter.castAndThen<BasePresenter<BaseActivity>> {
            it.initPresenter(this,
                    myApp.boxStore,
                    shared,
                    myApp.globalVars)
        }
    }

    lateinit var myApp: BaseApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = application as BaseApp
        setupPresenter()
    }




}