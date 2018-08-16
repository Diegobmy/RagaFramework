package com.example.ragabuza.baseragaapp.base

import android.support.annotation.StringRes

class M{
    companion object {
        operator fun minus(string: String): Message {
            return Message(string)
        }
        operator fun minus(@StringRes int: Int): Message {
            return Message(int)
        }
    }
}

operator fun Message.plus(function: UnitFunction): Pair<Message, UnitFunction> = this pairWith function