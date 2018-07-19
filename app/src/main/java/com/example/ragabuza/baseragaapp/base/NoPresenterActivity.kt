package com.example.ragabuza.baseragaapp.base

import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class NoPresenterActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    fun dismissDialog() {

    }

    fun showError(message: Message?) {

    }

    fun showMessage(message: Message?) {

    }

    fun showProgress(message: Message?) {

    }

}