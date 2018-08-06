package com.example.ragabuza.baseragaapp.base

import com.example.ragabuza.baseragaapp.base.dialog.DialogModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


abstract class API<T> : Observable<Response<T>>() {

    class ApiBuilder<T>(val api: API<T>){

        fun onSuccess(success: (response: T) -> Unit) {
            api.success = success
        }

        fun onError(error: (response: Message) -> Unit) {
            api.error = error
        }

        fun onBegin(begin: () -> Unit) {
            api.begin = begin
        }

        fun onComplete(complete: () -> Unit) {
            api.complete = complete
        }
    }

    var success: ((T) -> Unit)? = null
    var error: ((Message) -> Unit)? = null
    var begin: (() -> Unit)? = null
    var complete: (() -> Unit)? = null
    fun doSuccess(result: T) = success?.invoke(result)

    fun doError(view: BaseActivity?, result: Message){
        if (error != null){
            error?.invoke(result)
        } else view?.showDialog(DialogModel.error())
    }
    fun doBegin(view: BaseActivity?){
        if (begin != null){
            begin?.invoke()
        } else view?.showDialog(DialogModel.progress())
    }
    fun doComplete(view: BaseActivity?){
        if (complete != null){
            complete?.invoke()
        } else view?.dismissDialog()
    }

    private var builder: ApiBuilder<T>

    init {
           builder =  ApiBuilder(this)
    }


    fun cancel(){
        unsubscribeOn(Schedulers.io())
    }

    fun call(view: BaseActivity? = null, apiActions: ApiBuilder<T>.() -> Unit) {
        apiActions.invoke(builder)
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<T>> {
                    override fun onComplete() {
                        doComplete(view)
                    }

                    override fun onSubscribe(d: Disposable) {
                        doBegin(view)
                    }

                    override fun onNext(response: Response<T>) {
                        if (response.isSuccessful && response.body() != null) {
                            doSuccess(response.body()!!)
                        } else {
                            doError(view, Message(response.cast<Response<Any>>()!!))
                        }
                    }

                    override fun onError(e: Throwable) {
                        doError(view, Message(e.localizedMessage))
                    }


                })
    }

    fun silentCall(action: (T) -> Unit) {
        call {
            onSuccess(action)
        }
    }

}

