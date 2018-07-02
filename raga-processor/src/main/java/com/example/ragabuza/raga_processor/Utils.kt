package com.example.ragabuza.raga_processor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element
import kotlin.reflect.jvm.internal.impl.name.FqName
import kotlin.reflect.jvm.internal.impl.platform.JavaToKotlinClassMap


fun Element.getParameterName(): String {
    return simpleName.toString().drop(3).decapitalize()
}

fun Element.isNullable(): Boolean {
    return this.getAnnotation(org.jetbrains.annotations.Nullable::class.java) != null
}

fun Element.javaToKotlinType(): TypeName {
    return if (isNullable())
        asType().asTypeName().javaToKotlinType().asNullable()
    else
        asType().asTypeName().javaToKotlinType().asNonNullable()
}

private fun TypeName.javaToKotlinType(): TypeName {
    return if (this is ParameterizedTypeName) {
        ParameterizedTypeName.get(
                rawType.javaToKotlinType() as ClassName,
                *typeArguments.map {
                    if (rawType.isAnnotated) {
                        it.javaToKotlinType().asNullable()
                    } else {
                        it.javaToKotlinType().asNonNullable()
                    }
                }.toTypedArray()
        )
    } else {
        val className = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(FqName(toString()))
                ?.asSingleFqName()?.asString()

        return if (className == null) {
            this
        } else {
            ClassName.bestGuess(className)
        }
    }
}
