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
