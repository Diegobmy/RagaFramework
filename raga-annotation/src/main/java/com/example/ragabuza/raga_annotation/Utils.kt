package com.example.ragabuza.raga_annotation

fun <T, R> Iterable<T>.splitLists(condition: (T) -> R): HashMap<R, MutableList<T>> {
    val hashMap = hashMapOf<R, MutableList<T>>()
    forEach {
        val parameter = condition.invoke(it)
        if (hashMap[parameter] == null)
            hashMap[parameter] = mutableListOf(it)
        else
            hashMap[parameter]?.add(it)
    }
    return hashMap
}

fun String.capitalizeFirst(): String{
    return first().toUpperCase().toString() + if (length > 1) subSequence(1 until length) else ""
}

fun <T> T.equalsTo(vararg options: T): Boolean{
    return options.contains(this)
}
fun <T> T.notEqualsTo(vararg options: T): Boolean{
    return !options.contains(this)
}

fun <T1, T2> Pair<Iterable<T1>, Iterable<T2>>.forEach(action: (firstElement: T1, secondElement: T2) -> Unit) {
    first.forEach { t1 -> second.forEach { t2 -> action.invoke(t1, t2) } }
}