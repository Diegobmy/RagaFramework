package com.example.ragabuza.baseragaapp.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.Window
import com.example.ragabuza.baseragaapp.R

class RagaDialog(context: Context, @LayoutRes val layout: Int) : Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout)
    }
}