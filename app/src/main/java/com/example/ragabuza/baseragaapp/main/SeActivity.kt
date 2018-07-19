package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import butterknife.OnClick
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.BasePresenter
import com.example.ragabuza.baseragaapp.base.DoublePresenterActivity
import com.example.ragabuza.baseragaapp.base.Example
import com.example.ragabuza.raga_annotation.Parameter
import kotlinx.android.synthetic.main.nice.*

class SeActivity : DoublePresenterActivity(){
    override val secondPresenter = SePresenter()

    override val presenter = SePresenter()

    override var conditionFork = true

    override fun onCreateBase(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreatePrimary(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateSecondary(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @OnClick(R.id.show)
    fun show(){
        presenter.begin()
    }

    fun setText(string: String){
        info.text = string
    }

}