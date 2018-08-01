package com.example.ragabuza.baseragaapp.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.Window
import butterknife.ButterKnife

class RagaDialog(
        context: Context,
        @LayoutRes private val layout: Int,
        private val builder: DialogModel.Builder
) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout)
        setMain()
        setListeners()
        setColors()
    }

    private fun setColors() {

    }

    private fun setListeners() {
        if (builder.listeners.onDismiss != null){
            setOnDismissListener { builder.listeners.onDismiss?.invoke() }
        }

    }

    private fun setMain() {

    }

}