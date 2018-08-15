package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.support.v4.app.Fragment
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog

class CustomDialog<T>(val builder: Builder<T>) : DialogModel(builder) {
    override fun RagaDialog.setInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class Builder<T> : DialogModel.Builder() {
        var fragment: Fragment? = null
    }

}
