package com.example.ragabuza.raga_annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class Service(val baseUrl: String)