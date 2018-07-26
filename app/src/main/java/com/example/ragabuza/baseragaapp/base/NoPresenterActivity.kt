package com.example.ragabuza.baseragaapp.base

import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

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
//        showDialog(
//                DialogModel(DialogModel.Type.Error).create {
//                    this.message = message
//                }
//        )
    }

    fun showMessage(message: Message) {
//        showDialog(
//                DialogModel(DialogModel.Type.Message).create {
//                    this.message = message
//                }
//        )
    }

    fun showProgress(message: Message? = null) {
//        showDialog(
//                DialogModel(DialogModel.Type.Progress).create {
//                    this.message = message
//                }
//        )
    }

}