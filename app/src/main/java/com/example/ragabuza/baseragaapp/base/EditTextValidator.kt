package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class EditTextWithValidator : EditText {

    private val validations = mutableListOf<Pair<(String) -> Boolean, String>>()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun validateWhileWriting(showErrors: Boolean = false): EditTextWithValidator{
        afterTextChanged {
            validate(showErrors)
        }
        return this
    }

    fun validateWhenLeaving(showErrors: Boolean = false): EditTextWithValidator{
        setOnTouchListener { v, event ->
            if (!hasFocus()){
                validate(showErrors)
            }
            true
        }
        return this
    }

    fun addValidation(validation: (String) -> Boolean, message: Message) {
        validations.add(validation pairWith message.getMessage(context))
    }

    fun validate(showErrors: Boolean = false): Boolean {
        validations.forEach {
            val content = text.toString()
            if (!it.first.invoke(content)){
                if (showErrors)
                    error = it.second
                return false
            }
        }
        return true
    }

}