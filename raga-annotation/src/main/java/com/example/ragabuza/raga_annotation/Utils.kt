package com.example.ragabuza.raga_annotation


fun <T> Iterable<T>.splitLists(condition: (T) -> Any): HashMap<Any, MutableList<T>> {
    val hashMap = hashMapOf<Any, MutableList<T>>()
    forEach {
        val parameter = condition.invoke(it)
        if (hashMap[parameter] == null)
            hashMap[parameter] = mutableListOf(it)
        else
            hashMap[parameter]?.add(it)
    }
    return hashMap
}
