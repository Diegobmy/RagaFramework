package com.example.ragabuza.baseragaapp.base

import android.content.Context

fun Any?.isNull(): Boolean = this == null

fun Any?.isNotNull(): Boolean = this != null

inline fun <reified C> Any?.cast(): C? = if (this is C) this else null

inline fun <reified C> Any?.castAndThen(action: (C)->Unit) = if (this is C) action.invoke(this) else null

fun Context.getMessage(message: Message): String = message.getMessage(this)

fun resolveBooleans(vararg booleans: Boolean): Boolean{
    booleans.forEach {
        if (!it)
            return false
    }
    return true
}

fun Array<Boolean>.resolveBooleans(): Boolean{
    val array: BooleanArray = BooleanArray(size)
    forEachIndexed { index, b ->
        array[index] = b
    }
    return resolveBooleans(*array)
}

infix fun <T1, T2> T1.pairWith(second: T2): Pair<T1, T2> = Pair(this, second)