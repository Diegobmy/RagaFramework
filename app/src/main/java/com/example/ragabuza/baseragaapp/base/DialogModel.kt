package com.example.ragabuza.baseragaapp.base

import android.content.Context
import com.example.ragabuza.raga_annotation.Types

class DialogModel(val type: Type = Type.Custom) {

    @Types
    enum class Type {
        Progress,
        Error,
        Message,
        Custom
    }

    val myBuilder = builder()

    class builder {
        var title: Message? = null
        var message: Message? = null
    }

    fun create(type: Type = Type.Custom, actions: builder.() -> Unit): DialogModel {
        val dialogModel = DialogModel(type)
        actions.invoke(dialogModel.myBuilder)
        return dialogModel
    }

    fun show(context: Context) {
        if (type == Type.Custom) {

        }
    }

    fun dismiss() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}