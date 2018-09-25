package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.CheckBox
import android.widget.TextView
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.*
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog
import kotlinx.android.synthetic.main.dialog_combo.*

class ComboDialogModel<T>(val builder: Builder<T>) : DialogModel(builder) {

    val selected: MutableList<T> = mutableListOf()

    override fun RagaDialog.setInfo() {
        setContentView(R.layout.dialog_combo)

        closeButtonView = dialog_close
        titleView = dialog_title
        backgroungView = dialog_bg


        dialog_recycler.layoutManager = GridLayoutManager(context, builder.grid)
        val simpleAdapter = SimpleListAdapter(builder.list, R.layout.combo_item)
                .forId<CheckBox>(R.id.checkbox) { _, view, item ->
                    view.text = builder.getItemLabel(item)
                    view.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked)
                            selected.add(item)
                            else
                            selected.remove(item)
                    }
                }

        dialog_recycler.adapter = simpleAdapter

        if (dialog_positive.setVisible(
                        builder.positive.isNotNull()
                )) {
            dialog_positive.text = builder.positive?.first?.getMessage(context)
            dialog_positive.setOnClickListener {
                builder.positive?.second?.invoke(selected)
            }
        }
        builder.positiveColor.doIfNotNull {
            dialog_positive.setTextColor(context.getColorCompat(it))
        }

    }


    class Builder<T> : DialogModel.Builder(){
        val list = mutableListOf<T>()
        var getItemLabel: (item: T) -> String = { it.toString() }

        var positive: Pair<Message, ((List<T>) -> Unit)>? = null
        val grid: Int = 2
        var positiveColor: Int? = null
    }

}
