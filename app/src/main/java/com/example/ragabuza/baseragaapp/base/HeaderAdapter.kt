package com.example.ragabuza.baseragaapp.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class HeaderAdapter<ITEM : RecyclerView.ViewHolder, HEADER : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    override fun getItemViewType(position: Int): Int {
        return if (getHeadersPositions().sorted().contains(position)) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> onCreateHeaderHolder(parent)
            TYPE_ITEM -> onCreateItemHolder(parent)
            else -> throw RuntimeException("No existant view type: $viewType")
        }
    }

    abstract fun getHeadersPositions(): Set<Int>

    fun <T> getHeadersBasedOn(list: Iterable<T>, condition: (T) -> Any?): Set<Int> {
        val headers = mutableSetOf(0)
        var previous = condition.invoke(list.first())

        list.forEachIndexed { i, obj ->
            if (previous != condition.invoke(obj)) {
                previous = condition.invoke(obj)
                headers.add(i + headers.size)
            }
        }

        return headers
    }

    abstract fun onCreateItemHolder(parent: ViewGroup): ITEM

    abstract fun onCreateHeaderHolder(parent: ViewGroup): HEADER

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TYPE_HEADER -> {
                val headerPosition = getHeadersPositions().sorted().indexOf(position)
                onBindHeaderHolder(holder as HEADER, position, headerPosition)
            }
            TYPE_ITEM -> {
                val itemPosition = getItemPosition(position)
                onBindItemHolder(holder as ITEM, position, itemPosition)
            }
            else -> throw RuntimeException("No existant view type: $viewType")
        }
    }

    private fun getItemPosition(position: Int): Int {
        return position - getHeadersPositions().sorted().filter { it < position }.size
    }

    abstract fun onBindItemHolder(viewHolder: ITEM, viewPosition: Int, itemPosition: Int)

    abstract fun onBindHeaderHolder(viewHolder: HEADER, viewPosition: Int, headerPosition: Int)

    fun isHeader(position: Int): Boolean {
        return getHeadersPositions().sorted().contains(position)
    }

}