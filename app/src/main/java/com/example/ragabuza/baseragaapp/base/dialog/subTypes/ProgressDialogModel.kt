package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog

class ProgressDialogModel(builder: Builder) : DialogModel(builder){
    override fun RagaDialog.setInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class Builder : DialogModel.Builder(){
        var message: Message? = null

        var isPercentage = false
    }

    var percentage = 0

}
