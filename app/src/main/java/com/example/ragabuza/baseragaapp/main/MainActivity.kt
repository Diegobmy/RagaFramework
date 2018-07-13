package com.example.ragabuza.baseragaapp.main

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.BaseActivity
import com.example.ragabuza.baseragaapp.base.HeaderAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = adaoter(listOf(Pair("a",1),Pair("b",1),Pair("c",2),Pair("d",2),Pair("e",5),Pair("f",6)))
        recteste.adapter = adapter

    }

}

class adaoter(val itemList: List<Pair<String, Int>>) : HeaderAdapter<adaoter.ItemHolder, adaoter.HeaderHolder>() {
    override fun getItemCount(): Int {
        return itemList.size + getHeadersPositions().size
    }

    override fun getHeadersPositions(): Set<Int> {
        return getHeadersBasedOn(itemList) { it.second }
    }

    override fun onCreateItemHolder(parent: ViewGroup): ItemHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_test, parent, false)
        return ItemHolder(v)
    }

    override fun onCreateHeaderHolder(parent: ViewGroup): HeaderHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_test, parent, false)
        return HeaderHolder(v)
    }

    override fun onBindItemHolder(viewHolder: ItemHolder, viewPosition: Int, itemPosition: Int) {
        viewHolder.textView.text = itemList[itemPosition].first
    }

    override fun onBindHeaderHolder(viewHolder: HeaderHolder, viewPosition: Int, headerPosition: Int) {
        viewHolder.textView.text = "header ${itemList[getItemPosition(viewPosition)].second}"
    }

    class HeaderHolder(val view: View) : RecyclerView.ViewHolder(view){
        @BindView(R.id.text_view_test) lateinit var textView: TextView
        init {
            ButterKnife.bind(this, view)
        }
    }
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view){
        @BindView(R.id.text_view_test) lateinit var textView: TextView
        init {
            ButterKnife.bind(this, view)
        }
    }
}