package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import butterknife.OnClick
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Example
import com.example.ragabuza.baseragaapp.goToSeActivity
import com.example.ragabuza.raga_annotation.Parameter
import com.example.ragabuza.raga_annotation.Preference
import kotlinx.android.synthetic.main.nice.*

class SeActivity : BaseActivity(){

    @Preference("sharedTest") lateinit var test: String
    @Preference lateinit var ola: Example

    @Parameter companion object{}

    override val presenter = SePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nice)
        goToSeActivity()
    }

    @OnClick(R.id.show)
    fun show(){
        presenter.begin()
    }

    fun setText(string: String){
        info.text = string
    }

}