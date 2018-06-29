package com.example.ragabuza.baseragaapp.base

class WatchableList<T>(private val callback: (list: MutableList<T>) -> Unit): Iterable<T> {

    private var mutableList = mutableListOf<T>()

    constructor(vararg elements: T, callback: (list: MutableList<T>) -> Unit) : this(callback) {
        mutableList.addAll(elements)
    }

    private constructor(list: MutableList<T>, callback: (list: MutableList<T>) -> Unit) : this(callback) {
        mutableList = list
    }

    val size: Int
        get() = mutableList.size

    fun contains(element: T): Boolean = mutableList.contains(element)

    fun containsAll(elements: Collection<T>): Boolean = mutableList.containsAll(elements)

    operator fun get(index: Int): T = mutableList[index]

    fun indexOf(element: T): Int = mutableList.indexOf(element)

    fun isEmpty(): Boolean = mutableList.isEmpty()

    override fun iterator(): MutableIterator<T> = mutableList.iterator()

    fun lastIndexOf(element: T): Int = mutableList.lastIndexOf(element)

    fun add(element: T): Boolean {
        val result = mutableList.add(element)
        callback.invoke(mutableList)
        return result
    }

    fun add(index: Int, element: T) {
        val result = mutableList.add(index, element)
        callback.invoke(mutableList)
        return result
    }

    fun addAll(index: Int, elements: Collection<T>): Boolean {
        val result = mutableList.addAll(index, elements)
        callback.invoke(mutableList)
        return result
    }

    fun addAll(elements: Collection<T>): Boolean {
        val result = mutableList.addAll(elements)
        callback.invoke(mutableList)
        return result
    }

    fun clear() {
        mutableList.clear()
        callback.invoke(mutableList)
    }

    fun listIterator(): MutableListIterator<T> = mutableList.listIterator()

    fun listIterator(index: Int): MutableListIterator<T> = mutableList.listIterator(index)

    fun remove(element: T): Boolean {
        val result = mutableList.remove(element)
        callback.invoke(mutableList)
        return result
    }

    fun removeAll(elements: Collection<T>): Boolean {
        val result = mutableList.removeAll(elements)
        callback.invoke(mutableList)
        return result
    }

    fun removeAt(index: Int): T {
        val result = mutableList.removeAt(index)
        callback.invoke(mutableList)
        return result
    }

    fun retainAll(elements: Collection<T>): Boolean {
        val result = mutableList.addAll(elements)
        callback.invoke(mutableList)
        return result
    }

    fun set(index: Int, element: T): T {
        val result = mutableList.set(index, element)
        callback.invoke(mutableList)
        return result
    }

    fun subList(fromIndex: Int, toIndex: Int, callback: (list: MutableList<T>) -> Unit): WatchableList<T> {
        return WatchableList(mutableList.subList(fromIndex, toIndex), callback)
    }
}