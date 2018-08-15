package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.*
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog
import kotlinx.android.synthetic.main.dialog.*

class SimpleDialogModel(val builder: Builder) : DialogModel(builder) {

    class Builder : DialogModel.Builder() {
        var message: Message? = null
        var icon: Int? = null

        var iconColor: Int? = null
        var messageColor: Int? = null
        var positiveColor: Int? = null
        var negativeColor: Int? = null

        var positive: Pair<Message, () -> Unit>? = null
        var negative: Pair<Message, () -> Unit>? = null
    }

    override fun RagaDialog.setInfo() {
        setContentView(R.layout.dialog)

        closeButtonView = dialog_close
        titleView = dialog_title
        backgroungView = dialog_bg

        if (dialog_icon.setVisible(builder.icon.isNotNull())) {
            dialog_icon.setImageDrawable(context.getDrawable(builder.icon!!))
        }

        if (builder.message.isNotNull()) {
            dialog_message.text = builder.message?.getMessage(context)
        }

        if (dialog_negative.setVisible(
                        builder.negative.isNotNull()
                )) {
            dialog_negative.text = builder.negative?.first?.getMessage(context)
            dialog_negative.setOnClickListener { builder.negative?.second?.invoke() }
        }

        if (dialog_positive.setVisible(
                        builder.positive.isNotNull()
                )) {
            dialog_positive.text = builder.positive?.first?.getMessage(context)
            dialog_positive.setOnClickListener { builder.positive?.second?.invoke() }
        }
        builder.iconColor.doIfNotNull {
            dialog_icon.setColorFilter(context.getColorCompat(it))
        }

        builder.messageColor.doIfNotNull {
            dialog_message.setTextColor(context.getColorCompat(it))
        }
        builder.positiveColor.doIfNotNull {
            dialog_positive.setTextColor(context.getColorCompat(it))
        }
        builder.negativeColor.doIfNotNull {
            dialog_negative.setTextColor(context.getColorCompat(it))
        }


    }

}