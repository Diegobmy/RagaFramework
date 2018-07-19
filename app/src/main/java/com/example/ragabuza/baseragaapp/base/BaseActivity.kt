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
        presenter.cast<BasePresenter<BaseActivity>>()?.initPresenter(
                this,
                (application as BaseApp).boxStore,
                shared,
                (application as BaseApp).globalVars)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPresenter()
    }




}