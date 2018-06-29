package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.provider.ContactsContract
import butterknife.OnClick
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.Example
import com.example.ragabuza.raga_annotation.Parameter
import kotlinx.android.synthetic.main.nice.*

class SeActivity : BaseActivity(){

    @Parameter lateinit var um: List<String>
    @Parameter(true) lateinit var dois: List<String?>
    @Parameter var tres: List<String>? = null
    @Parameter(true) var quatro: List<String?>? = null

    override val presenter = SePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nice)
        loadSeActivityExtras()
    }

    @OnClick(R.id.show)
    fun show(){
        presenter.begin()
    }

    fun setText(string: String){
        info.text = string
    }

}