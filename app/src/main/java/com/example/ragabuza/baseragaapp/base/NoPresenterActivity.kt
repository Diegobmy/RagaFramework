package com.example.ragabuza.baseragaapp.base

import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class NoPresenterActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)

        SimpleDialogModel.info { dialogModel ->


        }

    }

    var currentDialog: SimpleDialogModel? = null

    fun dismissDialog(): Boolean {
        return if (currentDialog == null){
            false
        } else {
            currentDialog?.dismiss()
            currentDialog = null
            true
        }
    }

    fun showDialog(model: SimpleDialogModel, replace: Boolean = false): Boolean {
        return if (currentDialog != null && !replace) {
            false
        } else {
            currentDialog = model
            model.show(this)
            true
        }
    }

}