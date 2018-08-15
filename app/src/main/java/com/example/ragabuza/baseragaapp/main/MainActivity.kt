package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.m
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        save.setOnClickListener {
            showDialog(DialogModel.error { dialog ->
                title = Message("Nice")
                message = Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent risus nulla, tincidunt vel molestie non, mattis in urna. Vivamus viverra ultrices imperdiet.")
                negative = Pair(Message("nice"), {
                    dialog.builder.message = Message("uau")
                    dialog.refreshDialog()
                })
                iconColor = android.R.color.holo_blue_bright
                backgroundColor = android.R.color.holo_red_dark
                closeButtonColor = android.R.color.holo_orange_dark
            })

        }

    }

}
