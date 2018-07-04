package com.example.ragabuza.baseragaapp.main

import com.example.ragabuza.baseragaapp.base.*

class MainPresenter : BasePresenter<MainActivity>() {

    fun api() {
        MyService.create().hitCountCheck().apiCall(
                onSuccess = { response ->
                    view.toasmebaby(Message(response?.response ?: "204"))
                },
                onError = { error ->
                    view.toasmebaby(error)
                })

        val hello = WatchableList(1, 2) {

        }



        hello saveAs "hello"

        val hello2 = load<WatchableList<Int>>("hello")

        val examples = box<Example>().all
    }


    fun save(toString: String, toString1: String) {
        box<Example>().put(Example(0, toString, toString1))

    }


    fun get() {

        val examples = box<Example>().query().contains(Example_.name, view.getName()).build().find()
        view.loadData(examples.joinToString("\n"))
    }

    fun goNext() {
//        "nice job man" saveToSharedAs SharedValues.sharedTest
        view.goToNext()
    }


}
