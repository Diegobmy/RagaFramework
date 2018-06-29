package com.example.ragabuza.baseragaapp.base

import android.content.SharedPreferences
import com.google.gson.Gson
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.Query
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
    private lateinit var shared: SharedPreferences

    fun initPresenter(view: V, boxStore: BoxStore, shared: SharedPreferences, globalVars: HashMap<String, Any>) {
        this.view = view
        this.boxStore = boxStore
        this.globalVars = globalVars
        this.shared = shared
        onCreate()
    }

    internal inline infix fun <reified T> T.saveToSharedAs(value: SharedValues) {
        val editor = shared.edit()
        if (!value.isArray)
            when (value.clazz) {
                Boolean::class.java -> editor.putBoolean(value.name, this as Boolean)
                Float::class.java -> editor.putFloat(value.name, this as Float)
                Int::class.java -> editor.putInt(value.name, this as Int)
                Long::class.java -> editor.putLong(value.name, this as Long)
                String::class.java -> editor.putString(value.name, this as String)
                else -> editor.putString(value.name, Gson().toJson(this))
            }
        else
            when (value.clazz) {
                String::class.java -> editor.putStringSet(value.name, this as Set<String>)
                else -> {
                    val stringSet = mutableSetOf<String>()
                    (this as Iterable<*>).forEach {
                        stringSet.add(Gson().toJson(it))
                    }
                    editor.putStringSet(value.name, stringSet)
                }
            }
        editor.apply()
    }

    internal inline fun <reified T> SharedValues.get(): T = get(null as T)

    internal inline fun <reified T> SharedValues.get(default: T): T{
        return if (!isArray)
            when (clazz) {
                Boolean::class.java -> shared.getBoolean(name, default as Boolean) as T
                Float::class.java -> shared.getFloat(name, default as Float) as T
                Int::class.java -> shared.getInt(name, default as Int) as T
                Long::class.java -> shared.getLong(name, default as Long) as T
                String::class.java -> shared.getString(name, default as String) as T
                else -> Gson().fromJson(shared.getString(name, default as String), T::class.java)
            }
        else
            when (clazz) {
                String::class.java -> shared.getStringSet(name, default as Set<String>) as T
                else -> {
                    val set = mutableSetOf<Any>()
                    shared.getStringSet(name, default as Set<String>).forEach {
                        set.add(Gson().fromJson(it, clazz))
                    }
                    set as T
                }
            }
    }

    infix fun Any.saveAs(name: String) {
        globalVars[name] = this
    }

    internal inline fun <reified T> load(name: String, default: T): T {
        return load<T>(name) ?: default
    }

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
            if (it.key == className) return it.value as Box<B>
        }
        val box = boxStore.boxFor(B::class.java)
        cacheBoxes[className] = box as Box<Any>
        return box
    }

    fun <T> Observable<Response<T>>.apiCall(
            loadingMessage: Message? = null,
            onSuccess: (response: T?) -> Unit,
            onError: (message: Message) -> Unit
    ) {
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<T>> {
                    override fun onComplete() {
                        view.dismissDialog()
                    }

                    override fun onSubscribe(d: Disposable) {
                        if (loadingMessage.isNotNull()) {
                            view.showProgress(loadingMessage)
                        }
                    }

                    override fun onNext(response: Response<T>) {
                        if (response.isSuccessful) {
                            onSuccess.invoke(response.body())
                        } else {
                            onError.invoke(Message(response as Response<Any>))
                        }
                    }

                    override fun onError(e: Throwable) {
                        onError.invoke(Message(e.localizedMessage))
                    }


                })
    }

}
