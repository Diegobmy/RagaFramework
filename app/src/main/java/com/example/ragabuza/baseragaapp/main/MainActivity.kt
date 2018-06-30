package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.widget.Toast
import butterknife.OnClick
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.getMessage
import com.example.ragabuza.raga_annotation.Parameter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @OnClick(R.id.save)
    fun onSave(){
        presenter.save(name.text.toString(), type.text.toString())
        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.get)
    fun onGet(){

    }

    fun loadData(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    fun getName(): String {
        return name.text.toString()
    }

    fun goToNext() {
//        SeActivity.push(this, SeParams("funciona", 4))
    }

    fun toasmebaby(response: Message) {
        Toast.makeText(applicationContext, getMessage(response), Toast.LENGTH_LONG).show()
    }

}