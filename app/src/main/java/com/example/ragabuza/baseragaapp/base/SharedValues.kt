package com.example.ragabuza.baseragaapp.base

import android.content.Context
import com.example.ragabuza.raga_annotation.Parameter
import com.example.ragabuza.raga_annotation.SharedController
import com.google.gson.Gson

@SharedController class SharedValues(context: Context) {
    lateinit var testeString: String
    var testeInt: Int = 0
    var testeBoolean: Boolean = false
    lateinit var testeExample: Example
}