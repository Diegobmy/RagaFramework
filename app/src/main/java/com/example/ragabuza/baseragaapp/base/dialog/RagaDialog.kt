package com.example.ragabuza.baseragaapp.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.example.ragabuza.baseragaapp.base.*

class RagaDialog(
        val activity: NoPresenterActivity,
        private val model: DialogModel
) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        refreshDialog()
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun refreshDialog(){
        model.refreshDialog()
        setCanceledOnTouchOutside(model.myBuilder.dismissOnClickOutside)
    }

    override fun dismiss() {
        super.dismiss()
        model.myBuilder.onDismiss?.invoke()
        activity.currentDialog = null
    }

}