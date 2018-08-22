package com.example.ragabuza.baseragaapp.base.dialog.subTypes

import android.support.v7.widget.LinearLayoutManager
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


        dialog_recycler.layoutManager = LinearLayoutManager(context)
        val simpleAdapter = SimpleListAdapter(builder.list)
                .forId<TextView>(android.R.id.text1) { _, view, item ->
                    view.text = builder.getItemLabel(item)
                }.addClick { _, index, item ->
                    builder.onItemSelected?.invoke(item, index)
                }

        dialog_recycler.adapter = simpleAdapter

        if (dialog_filter.setVisible(builder.hasFilter)) {
            simpleAdapter.filterMethod = builder.onFilter
            dialog_filter.hint = builder.filterHint.getMessage(context)
            dialog_filter.afterTextChanged { s ->
                simpleAdapter.filter = s
            }
        }

    }

    class Builder<T> : DialogModel.Builder() {

        var hasFilter = false
        var onFilter: (item: T, filter: String) -> Boolean = { item, filter -> getItemLabel.invoke(item).toLowerCase().contains(filter.trim().toLowerCase()) }
            set(value) {
                hasFilter = true
                field = value
            }
        var filterHint = Message("filter")
            set(value) {
                hasFilter = true
                field = value
            }

        var list = mutableListOf<T>()
        var getItemLabel: (item: T) -> String = { it.toString() }

        var onItemSelected: ((item: T, index: Int) -> Unit)? = null
    }

}
