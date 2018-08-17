package com.example.ragabuza.baseragaapp.base

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SimpleListAdapter<T>(
        val list: MutableList<T>,
        @LayoutRes private val layout: Int = android.R.layout.simple_list_item_1
    ) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        return object : ViewHolder(v){}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        listOfActions.forEach {
            it.second.invoke(this, holder.itemView.findViewById(it.first), item)
        }
        holder.itemView.setOnClickListener { click?.invoke(this, position, item) }
    }

    private val listOfActions: MutableList<Pair<Int, (adapter: SimpleListAdapter<T>, view: View, item: T) -> Unit>> = mutableListOf()
    private var click: ((adapter: SimpleListAdapter<T>, index: Int, item: T) -> Unit)? = null

    fun <V : View> forId(@IdRes idRes: Int, action: (adapter: SimpleListAdapter<T>, view: V, item: T) -> Unit): SimpleListAdapter<T>{
        listOfActions.add(Pair(idRes, action as (SimpleListAdapter<T>, View, T) -> Unit))
        return this
    }

    fun addClick(action: (adapter: SimpleListAdapter<T>, index: Int, item: T) -> Unit): SimpleListAdapter<T>{
        click = action
        return this
    }

    fun justShowText(@IdRes idRes: Int): SimpleListAdapter<T>{
        listOfActions.add(Pair(idRes){ adapter, view, text -> (view as TextView).text = text.toString() })
        return this
    }

}