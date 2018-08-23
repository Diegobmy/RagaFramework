package com.example.ragabuza.baseragaapp.main

import com.example.ragabuza.baseragaapp.base.API
import com.example.ragabuza.raga_annotation.Service
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class MyServiceResponse(val response: String)

@Service("http://www.mocky.io/v2/") interface bananinha {

    @GET("5b2f05692f000084006a2952")
    fun hitCountCheck(): API<MyServiceResponse>

}