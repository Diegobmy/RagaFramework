package com.example.ragabuza.baseragaapp.base

import android.content.SharedPreferences
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


abstract class BasePresenter<V> where V : BaseActivity {

    lateinit var view: V
    lateinit var boxStore: BoxStore
    private lateinit var globalVars: HashMap<String, Any>
    lateinit var shared: SharedPreferences

    fun initPresenter(view: V, boxStore: BoxStore, shared: SharedPreferences, globalVars: HashMap<String, Any>) {
        this.view = view
        this.boxStore = boxStore
        this.globalVars = globalVars
        this.shared = shared
        onCreate()
    }

    infix fun Any.saveAs(name: String) {
        globalVars[name] = this
    }

    internal inline fun <reified T> load(name: String, default: T): T = load<T>(name) ?: default
    internal inline fun <reified T> load(name: String): T? {
        val result = if (globalVars[name] is T)
            globalVars[name] as T
        else
            null
        globalVars.remove(name)
        return result
    }

    fun onCreate() {}

    private val cacheBoxes = HashMap<String, Box<Any>>()
    internal inline fun <reified B> box(): Box<B> {
        val className = B::class.java.simpleName
        cacheBoxes.forEach {
            if (it.key == className) return it.value.cast<Box<B>>()!!
        }
        val box = boxStore.boxFor(B::class.java)
        cacheBoxes[className] = box.cast<Box<Any>>()!!
        return box
    }

    internal val cacheServices = HashMap<String, Any>()
}
