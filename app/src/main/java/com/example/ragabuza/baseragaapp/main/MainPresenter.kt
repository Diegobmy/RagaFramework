package com.example.ragabuza.baseragaapp.main

import com.example.ragabuza.baseragaapp.base.*
import com.example.ragabuza.baseragaapp.getbananinha

class MainPresenter : BasePresenter<MainActivity>() {

    fun teste(){
        getbananinha().hitCountCheck().call {
            onSuccess {  }
        }
    }

}
