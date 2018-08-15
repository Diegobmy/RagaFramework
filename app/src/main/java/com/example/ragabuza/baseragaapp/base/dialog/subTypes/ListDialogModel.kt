package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog

class ListDialogModel<T>(val builder: Builder<T>) : DialogModel(builder) {
    override fun RagaDialog.setInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class Builder<T> : DialogModel.Builder() {

        var hasFilter = false

        val list = mutableListOf<T>()
        var getItemLabel: ((T) -> String)? = null

        var onItemSelected: ((T, Int) -> Unit)? = null
    }

}
