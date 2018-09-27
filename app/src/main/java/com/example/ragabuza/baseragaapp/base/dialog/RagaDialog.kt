package com.example.ragabuza.baseragaapp.base.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.*
import com.example.ragabuza.baseragaapp.base.dialog.subTypes.CustomDialog

class RagaDialog : DialogFragment() {
    lateinit var model: DialogModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(model.viewId, container, false)
    }

    override fun onResume() {
        super.onResume()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        refreshDialog()
    }

    fun refreshDialog() {
        model.refreshDialog()
        dialog.setCanceledOnTouchOutside(model.myBuilder.dismissOnClickOutside)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        model.myBuilder.onDismiss?.invoke()
        (activity as BaseActivity).currentDialog = null
    }

}