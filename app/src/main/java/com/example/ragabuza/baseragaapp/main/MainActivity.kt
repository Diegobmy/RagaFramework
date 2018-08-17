package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.widget.Toast
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lista = mutableListOf(1,2,3,4,5,6,7)

        save.setOnClickListener {
            showDialog(DialogModel.list<Int> {
                list = lista
//                hasFilter = true
                onItemSelected = {item, index -> Toast.makeText(this@MainActivity, item.toString(), Toast.LENGTH_SHORT).show() }
            })

        }

    }

}
