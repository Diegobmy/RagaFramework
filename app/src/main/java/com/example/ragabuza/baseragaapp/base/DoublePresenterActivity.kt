package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class DoublePresenterActivity : BaseActivity() {

    abstract val secondPresenter: BasePresenter<out BaseActivity>

    private fun setupSecondaryPresenter() {
        secondPresenter.castAndThen<BasePresenter<BaseActivity>> {
            it.initPresenter(this,
                    myApp.boxStore,
                    shared,
                    myApp.globalVars)
        }
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