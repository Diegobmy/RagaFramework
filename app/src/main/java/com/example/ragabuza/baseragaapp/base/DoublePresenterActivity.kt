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
        (secondPresenter as? BasePresenter<BaseActivity>)?.initPresenter(
                this,
                (application as BaseApp).boxStore,
                shared,
                (application as BaseApp).globalVars)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSecondaryPresenter()
    }


}