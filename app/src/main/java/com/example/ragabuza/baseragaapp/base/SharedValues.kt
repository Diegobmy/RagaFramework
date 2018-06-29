package com.example.ragabuza.baseragaapp.base

enum class SharedValues(val clazz: Class<*>, val isArray: Boolean) {
    sharedTest(String::class.java, false)
}