package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.widget.Toast
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.SimpleListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = SimpleListAdapter(R.layout.recycler_test, mutableListOf("a", "b", "c", "d"))
                .justShowText(R.id.text_view_test)
                .addClick { adapter, position, item ->
                    adapter.list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }

        recteste.adapter = adapter


    }

}