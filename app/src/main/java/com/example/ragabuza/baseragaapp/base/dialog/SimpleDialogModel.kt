package com.example.ragabuza.baseragaapp.base.dialog

import android.support.annotation.ColorRes
import com.example.ragabuza.baseragaapp.base.Message

class SimpleDialogModel(builder: SimpleDialogModel.Builder) : DialogModel(builder) {

    class Builder{
        val listeners = BuilderListeners()
        val colors = BuilderColors()

        var title: Message? = null
        var message: Message? = null
        var closeButton: Boolean = true
        var dismissOnClickOutside: Boolean = true //todo dismiss on outside click
        var icon: Int? = null
    }

    class BuilderListeners {
        var positive: Pair<Message, () -> Unit>? = null
        var negative: Pair<Message, () -> Unit>? = null
        var onDismiss: (() -> Unit)? = null
    }

    class BuilderColors {
        @ColorRes
        var iconTint: Int? = null
        @ColorRes
        var messageColor: Int? = null
        @ColorRes
        var titleColor: Int? = null
        @ColorRes
        var positiveColor: Int? = null
        @ColorRes
        var negativeColor: Int? = null
        @ColorRes
        var background: Int? = null
    }

}