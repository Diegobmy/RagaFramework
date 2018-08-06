package com.example.ragabuza.baseragaapp.base.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ragabuza.baseragaapp.R
import android.view.Window
import com.example.ragabuza.baseragaapp.base.*
import kotlinx.android.synthetic.main.dialog.*

class RagaDialog(
        context: Context,
        private val builder: Any
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog)
        when (builder) {
            is SimpleDialogModel.Builder -> {
                setSimple(builder)
            }
        }
    }

    private fun setSimple(builder: SimpleDialogModel.Builder) {
        if (dialog_close.setVisible(builder.closeButton.isNotNull())) {
            dialog_close.setOnClickListener { dismiss() }
        }

        if (dialog_icon.setVisible(builder.icon.isNotNull())) {
            dialog_icon.setImageDrawable(context.getDrawable(builder.icon!!))
        }

        if (builder.message.isNotNull()){
            dialog_message.text = builder.message?.getMessage(context)
        }

        if (builder.title.isNotNull()){
            dialog_title.text = builder.title?.getMessage(context)
        }
        if (builder.listeners.onDismiss.isNotNull())
            setOnDismissListener { builder.listeners.onDismiss?.invoke() }

        if (dialog_negative.setVisible(
                        builder.listeners.negative.isNotNull()
                )) {
            dialog_negative.text = builder.listeners.negative?.first?.getMessage(context)
            dialog_negative.setOnClickListener { builder.listeners.negative?.second?.invoke() }
        }

        if (dialog_positive.setVisible(
                        builder.listeners.positive.isNotNull()
                )) {
            dialog_positive.text = builder.listeners.positive?.first?.getMessage(context)
            dialog_positive.setOnClickListener { builder.listeners.positive?.second?.invoke() }
        }
        builder.colors.background.doIfNotNull {
            dialog_bg.setBackgroundColor(context.getColorCompat(it))
        }
        builder.colors.iconTint.doIfNotNull {
            //todo color icon
        }
        builder.colors.titleColor.doIfNotNull {
            dialog_title.setTextColor(context.getColorCompat(it))
        }
        builder.colors.messageColor.doIfNotNull {
            dialog_message.setTextColor(context.getColorCompat(it))
        }
        builder.colors.positiveColor.doIfNotNull {
            dialog_positive.setTextColor(context.getColorCompat(it))
        }
        builder.colors.negativeColor.doIfNotNull {
            dialog_negative.setTextColor(context.getColorCompat(it))
        }

    }


}