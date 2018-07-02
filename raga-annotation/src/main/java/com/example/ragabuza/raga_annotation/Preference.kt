package com.example.ragabuza.raga_annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class Preference(val key: String = "")
