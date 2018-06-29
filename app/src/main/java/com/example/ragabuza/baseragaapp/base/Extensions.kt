package com.example.ragabuza.baseragaapp.base

import android.content.Context

fun Any?.isNull(): Boolean{
    return this == null
}
fun Any?.isNotNull(): Boolean{
    return this != null
}

fun Context.getMessage(message: Message): String {
    return message.getMessage(this)
}