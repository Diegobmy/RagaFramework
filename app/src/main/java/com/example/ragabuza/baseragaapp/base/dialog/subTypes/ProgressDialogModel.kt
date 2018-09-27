package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.widget.ProgressBar
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog
import com.example.ragabuza.baseragaapp.base.setVisible
import kotlinx.android.synthetic.main.dialog_progress.*
import android.animation.ValueAnimator
import com.example.ragabuza.baseragaapp.base.UnitFunction


class ProgressDialogModel(val builder: Builder) : DialogModel(builder) {

    override val viewId: Int = R.layout.dialog_progress

    override fun RagaDialog.setInfo() {

        percentage = 0
        animatedInt = 0

        closeButtonView = dialog_close
        titleView = dialog_title
        backgroungView = dialog_bg

        if (builder.isPercentage) {
            dialog_progress_bar.progress = 0
            dialog_progress_bar.setVisible(false)
            dialog_progress_bar_horizontal.setVisible(true)
        } else {
            dialog_progress_bar.setVisible(true)
            dialog_progress_bar_horizontal.setVisible(false)
        }

        if (dialog_message.setVisible(builder.message != null))
            dialog_message.text = builder.message?.getMessage(context!!)
    }


    class Builder : DialogModel.Builder() {

        var message: Message? = null
        var isPercentage = false
        var onComplete: UnitFunction? = null
            set(value) {
                field = value
                isPercentage = true
            }
        var estimatedTime = 4000
            set(value) {
                field = value
                isPercentage = true
            }
    }

    private var animatedInt: Int = 0
    var percentage = 0
        set(value) {
            if (!(value == 0 && animatedInt == 0)) {
                val animator = ValueAnimator.ofInt(animatedInt, value)
                val time = Math.abs(animatedInt - value)
                animator.duration = if (value < 100)
                    ((time * builder.estimatedTime) / 100).toLong()
                else
                    2000L
                animator.addUpdateListener { animation ->
                    animatedInt = animation.animatedValue as Int
                    dialog?.view?.findViewById<ProgressBar>(R.id.dialog_progress_bar_horizontal)?.progress = animatedInt
                    if (animatedInt >= 100)
                        builder.onComplete?.invoke()
                }
                animator.start()
            }
            field = value
        }
}
