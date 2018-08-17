package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import com.example.ragabuza.baseragaapp.base.dialog.RagaDialog
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.*
import kotlinx.android.synthetic.main.dialog_list.*

class ListDialogModel<T>(val builder: Builder<T>) : DialogModel(builder) {
    override fun RagaDialog.setInfo() {
        setContentView(R.layout.dialog_list)

        closeButtonView = dialog_close
        titleView = dialog_title
        backgroungView = dialog_bg

        var listCopy = builder.list

        dialog_recycler.adapter = SimpleListAdapter(listCopy)
                .forId<TextView>(android.R.id.text1) { _, view, item ->
                    view.text = builder.getItemLabel(item)
                }.addClick { _, index, item ->
                    builder.onItemSelected?.invoke(item, index)
                }

        if (dialog_filter.setVisible(builder.hasFilter)) {
            dialog_filter.hint = builder.filterHint.getMessage(context)
            dialog_filter.afterTextChanged { s ->
                listCopy = builder.list.filter { builder.onFilter.invoke(it, s) }.toMutableList()
            }
        }

    }

    class Builder<T> : DialogModel.Builder() {

        var hasFilter = false
        var onFilter: (item: T, filter: String) -> Boolean = {item, filter -> item.toString().contains(filter) }
        var filterHint = Message("filter")

        var list = mutableListOf<T>()
        var getItemLabel: (item: T) -> String = { it.toString() }

        var onItemSelected: ((item: T, index: Int) -> Unit)? = null
    }

}
