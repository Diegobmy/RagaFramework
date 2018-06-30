package com.example.ragabuza.raga_annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class Parameter(val nullableArray: Boolean = false)
