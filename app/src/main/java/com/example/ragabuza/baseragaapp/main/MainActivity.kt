package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.os.Handler
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.subTypes.ProgressDialogModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progress = DialogModel.progress {
            message = Message("nice")
            title = Message("nice nice nice nice")
            estimatedTime = 40000
            onComplete = {
                it.dismiss()
            }
        }

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                progress.percentage += 10
                if (progress.percentage < 100)
                    handler.postDelayed(this, 2000)
            }
        }

//Start


        save.setOnClickListener {
            showDialog(progress)
            handler.postDelayed(runnable, 2000)
        }


    }

}
