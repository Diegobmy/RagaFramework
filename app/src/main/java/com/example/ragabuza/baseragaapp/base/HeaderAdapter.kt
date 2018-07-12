package com.example.ragabuza.baseragaapp.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class HeaderAdapter<ITEM : RecyclerView.ViewHolder, HEADER : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    private val headerPositions = mutableListOf<Int>()

    override fun getItemViewType(position: Int): Int {
        return if (headerPositions.contains(position)){
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return getItemCountWithoutHeader() + headerPositions.size
    }

    abstract fun getItemCountWithoutHeader(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> onCreateHeaderHolder(parent)
            TYPE_ITEM -> onCreateItemHolder(parent)
            else -> throw RuntimeException("No existant view type: $viewType")
        }
    }

    abstract fun onCreateItemHolder(parent: ViewGroup): ITEM

    abstract fun onCreateHeaderHolder(parent: ViewGroup): HEADER

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        return when {
            getItemViewType(position) == TYPE_HEADER -> onBindHeaderHolder(holder as HEADER, position, headerPositions.indexOf(position))
            getItemViewType(position) == TYPE_ITEM -> onBindItemHolder(holder as ITEM, position, getItemPosition(position))
            else -> throw RuntimeException("No existant view type: $viewType")
        }
    }

    private fun getItemPosition(position: Int): Int {
        headerPositions.forEachIndexed { index, i ->
            if (position > i){
                return position + index
            }
        }
        return position
    }

    abstract fun onBindItemHolder(viewHolder: ITEM, viewPosition: Int, itemPosition: Int)

    abstract fun onBindHeaderHolder(viewHolder: HEADER, viewPosition: Int, headerPosition: Int)

    fun markAsHeader(vararg position: Int){
        headerPositions.addAll(position.asList())
        headerPositions.sort()
    }

    fun unMarkAsHeader(vararg position: Int){
        headerPositions.removeAll(position.asList())
        headerPositions.sort()
    }

    fun isHeader(position: Int): Boolean{
        return headerPositions.contains(position)
    }

}