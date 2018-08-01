package com.example.ragabuza.baseragaapp.base

import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.example.ragabuza.baseragaapp.ErrorDialogModel
import com.example.ragabuza.baseragaapp.MessageDialogModel
import com.example.ragabuza.baseragaapp.ProgressDialogModel

abstract class NoPresenterActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    var currentDialog: DialogModel? = null

    fun dismissDialog(type: DialogModel.Type? = null): Boolean {
        return if (type != null && currentDialog?.type == type){
            false
        } else {
            currentDialog?.dismiss()
            currentDialog = null
            true
        }
    }

    fun showDialog(model: DialogModel, replace: Boolean = false): Boolean {
        return if (currentDialog != null && !replace) {
            false
        } else {
            currentDialog = model
            model.show(this)
            true
        }
    }

    fun showError(message: Message) {
        ErrorDialogModel{ it.message = message }
    }

    fun showMessage(message: Message) {
        MessageDialogModel { it.message = message }
    }

    fun showProgress(message: Message? = null) {
        ProgressDialogModel { it.message = message }
    }

}