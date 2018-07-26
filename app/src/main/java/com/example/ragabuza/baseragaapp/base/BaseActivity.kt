package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class BaseActivity : NoPresenterActivity() {

    abstract val presenter: BasePresenter<out BaseActivity>

    lateinit var shared: SharedPreferences

    val myApp = application as BaseApp

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPresenter()
    }




}