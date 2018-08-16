package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.M
import com.example.ragabuza.baseragaapp.base.pairWith
import com.example.ragabuza.baseragaapp.base.plus

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        save.setOnClickListener {
            showDialog(DialogModel.error { dialog ->
                title = M- "Nice"
                message = M- "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent risus nulla, tincidunt vel molestie non, mattis in urna. Vivamus viverra ultrices imperdiet."
                negative = M- "nice" + {
                    dialog.builder.message = M- "uau"
                    dialog.refreshDialog()
                }
                iconColor = android.R.color.holo_blue_bright
                backgroundColor = android.R.color.holo_red_dark
                closeButtonColor = android.R.color.holo_orange_dark
            })

        }

    }

}
