package com.example.ragabuza.baseragaapp.base.dialog

import android.support.annotation.ColorRes
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.*
import kotlinx.android.synthetic.main.dialog.*

class SimpleDialogModel(val builder: SimpleDialogModel.Builder) : DialogModel(builder) {

    class Builder : DialogModel.Builder() {
        var message: Message? = null
        var icon: Int? = null

        var iconTint: Int? = null
        var messageColor: Int? = null
        var titleColor: Int? = null
        var positiveColor: Int? = null
        var negativeColor: Int? = null
        var background: Int? = null

        var positive: Pair<Message, () -> Unit>? = null
        var negative: Pair<Message, () -> Unit>? = null
    }

    override fun RagaDialog.setInfo() {

        setContentView(R.layout.dialog)

        if (dialog_close.setVisible(builder.closeButton.isNotNull())) {
            dialog_close.setOnClickListener { dismiss() }
        }

        if (dialog_icon.setVisible(builder.icon.isNotNull())) {
            dialog_icon.setImageDrawable(context.getDrawable(builder.icon!!))
        }

        if (builder.message.isNotNull()) {
            dialog_message.text = builder.message?.getMessage(context)
        }

        if (builder.title.isNotNull()) {
            dialog_title.text = builder.title?.getMessage(context)
        }
        if (builder.onDismiss.isNotNull())
            setOnDismissListener { builder.onDismiss?.invoke() }

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
        builder.background.doIfNotNull {
            dialog_bg.setBackgroundColor(context.getColorCompat(it))
        }
        builder.iconTint.doIfNotNull {
            //todo color icon
        }
        builder.titleColor.doIfNotNull {
            dialog_title.setTextColor(context.getColorCompat(it))
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