package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class DoublePresenterActivity : BaseActivity() {

    abstract val secondPresenter: BasePresenter<out BaseActivity>

    private fun setupSecondaryPresenter() {
        shared = getSharedPreferences(
                packageName, Context.MODE_PRIVATE)
        secondPresenter.cast<BasePresenter<BaseActivity>>()?.initPresenter(
                this,
                (application as BaseApp).boxStore,
                shared,
                (application as BaseApp).globalVars)
    }

    abstract var conditionFork: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSecondaryPresenter()
        onCreateBase(savedInstanceState)
        if (conditionFork){
            onCreatePrimary(savedInstanceState)
        } else {
            onCreateSecondary(savedInstanceState)
        }
    }

    abstract fun onCreateBase(savedInstanceState: Bundle?)
    abstract fun onCreatePrimary(savedInstanceState: Bundle?)
    abstract fun onCreateSecondary(savedInstanceState: Bundle?)


}