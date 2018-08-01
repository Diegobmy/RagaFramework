package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.raga_annotation.Types

class DialogModel(val type: Type, val actions: Builder.() -> Unit) {

    @Types
    enum class Type {
        Error,
        Message,
        Options
    }

    private val myBuilder: Builder

    init {
        myBuilder = Builder()
        actions.invoke(myBuilder)
    }

    class Builder {
        val listeners = BuilderListeners()
        val colors = BuilderColors()

        var title: Message? = null
        var message: Message? = null
        var closeButton: Boolean = true
        var dismissOnClickOutside: Boolean = true
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

    var dialog: RagaDialog? = null

    fun show(context: Context) {
        if (dialog == null)
            dialog = getDialogForType(context)
        dialog?.show()
    }

    private fun getDialogForType(context: Context): RagaDialog {
        when (type) {
            DialogModel.Type.Error -> {
                myBuilder.icon.doIfNotNull { myBuilder.icon = R.drawable.ic_error_dialog }
            }
            DialogModel.Type.Message -> {

            }
            DialogModel.Type.Options -> {

            }
        }
        return RagaDialog(context, R.layout.dialog, myBuilder)
    }

    fun dismiss() {
        dialog?.dismiss()
    }

}