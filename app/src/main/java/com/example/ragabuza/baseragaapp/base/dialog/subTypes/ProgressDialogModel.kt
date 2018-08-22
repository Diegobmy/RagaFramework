package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.widget.ProgressBar
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog
import com.example.ragabuza.baseragaapp.base.setVisible
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.dialog_progress.view.*

class ProgressDialogModel(val builder: Builder) : DialogModel(builder) {
    override fun RagaDialog.setInfo() {
        setContentView(R.layout.dialog_progress)

        closeButtonView = dialog_close
        titleView = dialog_title
        backgroungView = dialog_bg

        if (builder.isPercentage)
            dialog_progress_bar.progress = 0

        if (dialog_message.setVisible(builder.message != null))
            dialog_message.text = builder.message?.getMessage(context)
    }


    class Builder : DialogModel.Builder() {
        var message: Message? = null

        var isPercentage = false
    }

    var percentage = 0
        set(value) {
            field = value
            dialog!!.findViewById<ProgressBar>(R.id.dialog_progress_bar)!!.progress = value
        }

}
