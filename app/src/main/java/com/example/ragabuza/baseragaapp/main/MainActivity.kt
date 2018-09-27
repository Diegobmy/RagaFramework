package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.subTypes.ProgressDialogModel
import com.example.ragabuza.baseragaapp.base.pairWith
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = MainFragment()

        val dialog = DialogModel.custom(fragment)



//Start


        save.setOnClickListener {
            showDialog(dialog)
        }


    }

}
