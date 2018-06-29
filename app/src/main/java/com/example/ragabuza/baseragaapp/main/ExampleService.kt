package com.example.ragabuza.baseragaapp.main

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class MyServiceResponse(val response: String)

interface MyService {

    @GET("5b2f05692f000084006a2952")
    fun hitCountCheck(): Observable<Response<MyServiceResponse>>

    companion object {
        fun create(): MyService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://www.mocky.io/v2/")
                    .build()

            return retrofit.create(MyService::class.java)
        }
    }

}