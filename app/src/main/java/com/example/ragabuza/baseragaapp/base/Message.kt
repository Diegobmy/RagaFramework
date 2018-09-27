package com.example.ragabuza.baseragaapp.base

import android.content.Context
import android.support.annotation.StringRes
import org.json.JSONObject
import retrofit2.Response


class Message {

    private var rawMessage: String? = null
    private var resMessage: Int? = null
    private var varArgs: Array<out Any>? = null

    private val errorString = "ERROR"

    constructor(message: String) {
        rawMessage = message
        resMessage = null
        varArgs = null
    }

    constructor(@StringRes message: Int) {
        resMessage = message
        rawMessage = null
        varArgs = null
    }

    constructor(@StringRes message: Int, vararg formatArgs: Any) {
        resMessage = message
        rawMessage = null
        varArgs = formatArgs
    }

    constructor(response: Response<Any>) {
        resMessage = null
        rawMessage = try {
            val jsonObject = JSONObject(response.errorBody()!!.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            response.message()
        }
    }

    fun getMessage(context: Context): String {
        if (rawMessage.isNotNull())
            return rawMessage ?: errorString

        if (resMessage.isNotNull() && varArgs.isNull())
            return try {
                context.getString(resMessage!!)
            } catch (e: Exception) {
                errorString
            }
        else if (varArgs.isNotNull())
            return try {
                context.getString(resMessage!!, varArgs)
            } catch (e: Exception) {
                errorString
            }

        return errorString
    }

}