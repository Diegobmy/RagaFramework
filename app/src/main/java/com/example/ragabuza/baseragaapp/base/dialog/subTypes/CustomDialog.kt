package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog
import kotlinx.android.synthetic.main.dialog_custom.*
import android.R.attr.tag
import android.view.View
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.UnitFunction


class CustomDialog<out T>(val builder: Builder<T>) : DialogModel(builder) where T : Fragment {

    override val viewId: Int = R.layout.dialog_custom

    override fun RagaDialog.setInfo() {

        closeButtonView = dialog_close
        titleView = dialog_title
        backgroungView = dialog_bg

        childFragmentManager.beginTransaction().replace(R.id.dialog_frame_layout, builder.fragment).commit()

        if (builder.title == null)
            titleView?.visibility = View.GONE

        if (builder.buttonA == null)
            dialog_btn_a.visibility = View.GONE
        else {
            dialog_btn_a.text = builder.buttonA?.first?.getMessage(context!!)
            dialog_btn_a.setOnClickListener { builder.buttonA?.second?.invoke() }
        }

        if (builder.buttonB == null)
            dialog_btn_b.visibility = View.GONE
        else {
            dialog_btn_b.text = builder.buttonB?.first?.getMessage(context!!)
            dialog_btn_b.setOnClickListener { builder.buttonB?.second?.invoke() }
        }

    }

    class Builder<out T>(val fragment: T) : DialogModel.Builder() where T : Fragment {
        var buttonA: Pair<Message, UnitFunction>? = null
        var buttonB: Pair<Message, UnitFunction>? = null
    }

}
