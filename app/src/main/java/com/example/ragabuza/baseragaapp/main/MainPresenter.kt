package com.example.ragabuza.baseragaapp.main

import com.example.ragabuza.baseragaapp.base.*
import com.example.ragabuza.baseragaapp.getbananinha
import com.example.ragabuza.baseragaapp.loadPlumpinha
import com.example.ragabuza.baseragaapp.savePlumpinha

class MainPresenter : BasePresenter<MainActivity>() {

    fun teste(){
        getbananinha().hitCountCheck().call {
            onSuccess { it -> it.response }
            onBegin {  }
            onComplete {  }
            onError {  }
        }
    }

}
