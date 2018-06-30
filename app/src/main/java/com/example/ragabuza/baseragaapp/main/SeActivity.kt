package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.provider.ContactsContract
import butterknife.OnClick
import com.example.ragabuza.baseragaaap.goToSeActivity
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Example
import com.example.ragabuza.raga_annotation.Parameter
import kotlinx.android.synthetic.main.nice.*

class SeActivity : BaseActivity(){

    @Parameter companion object {
        lateinit var nullList: List<String?>
        lateinit var nonNullList: List<Example>
    }

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