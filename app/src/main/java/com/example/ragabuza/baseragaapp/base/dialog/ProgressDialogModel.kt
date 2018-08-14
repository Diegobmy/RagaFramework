package com.example.ragabuza.baseragaapp.base.dialog

import com.example.ragabuza.baseragaapp.base.Message

class ProgressDialogModel(builder: ProgressDialogModel.Builder) : DialogModel(builder){
    override fun RagaDialog.setInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class Builder : DialogModel.Builder(){
        var message: Message? = null

        var isPercentage = false
    }

    var percentage = 0

}
