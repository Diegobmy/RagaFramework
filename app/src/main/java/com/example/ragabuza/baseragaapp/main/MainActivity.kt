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

        val progress = DialogModel.combo<String> {
            list.add("1")
            list.add("2")
            list.add("3")
            list.add("4")
            list.add("5")

            positive = Message("conf") pairWith { list -> Toast.makeText(applicationContext, list.toString(), Toast.LENGTH_SHORT).show() }
        }



//Start


        save.setOnClickListener {
            showDialog(progress)
        }


    }

}
