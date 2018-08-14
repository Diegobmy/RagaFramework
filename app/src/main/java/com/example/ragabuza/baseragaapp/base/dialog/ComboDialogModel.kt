package com.example.ragabuza.baseragaapp.base.dialog

import com.example.ragabuza.baseragaapp.base.Message

class ComboDialogModel<T>(val builder: ComboDialogModel.Builder<T>) : DialogModel(builder) {
    override fun RagaDialog.setInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class Builder<T> : DialogModel.Builder(){

        var hasFilter = false

        val list = mutableListOf<T>()
        var getItemLabel: ((T) -> String)? = null

        var onConfirm: ((List<T>, List<Int>) -> Unit)? = null

    }

}
