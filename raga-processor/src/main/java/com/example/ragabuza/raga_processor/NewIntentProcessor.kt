package com.example.ragabuza.raga_processor

import com.example.ragabuza.raga_annotation.Parameter
import com.example.ragabuza.raga_annotation.splitLists
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import kotlin.reflect.jvm.internal.impl.name.FqName
import kotlin.reflect.jvm.internal.impl.platform.JavaToKotlinClassMap

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(NewIntentProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class NewIntentProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        roundEnv.getElementsAnnotatedWith(Parameter::class.java).splitLists { it.enclosingElement }.forEach {
            generateNewMethod(it.key as Element, it.value)
        }

        return false
    }

    private fun generateNewMethod(activity: Element, parameters: MutableList<Element>) {
        val generatedSourcesRoot: String = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
        val packageOfMethod = processingEnv.elementUtils.getPackageOf(activity).toString()
        val fileName = activity.simpleName.toString() + "Navigator"

        val navClass = TypeSpec.classBuilder(fileName)
        val companion = TypeSpec.companionObjectBuilder()

        val activityName = activity.simpleName.toString()
        val activityClass = ClassName(packageOfMethod, activityName)

        val goToFuncBuilder = FunSpec.builder("goTo$activityName")
                .addModifiers(KModifier.PUBLIC)
                .receiver(ClassName("android.content", "Context"))

        val loadFuncBuilder = FunSpec.builder("load${activityName}Extras")
                .addModifiers(KModifier.PUBLIC)
                .receiver(activityClass)

        parameters.forEach {
            val type = it.javaToKotlinType(it.getAnnotation(Parameter::class.java).nullableArray)
            val parameterName = it.simpleName.toString()
            goToFuncBuilder.addParameter(parameterName, type)

            goToFuncBuilder.addCode("$fileName.$parameterName = $parameterName\n")
            if (type.isLateInit() && !it.isNullable())
                companion.addProperty(PropertySpec.varBuilder(parameterName, type, KModifier.LATEINIT).build())
            else
                companion.addProperty(PropertySpec.varBuilder(parameterName, type).initializer("null").build())

            loadFuncBuilder.addStatement("$parameterName = $fileName.$parameterName")
        }

        goToFuncBuilder.addStatement("val intent = %T(this, %T::class.java)",
                ClassName("android.content", "Intent"),
                activityClass)
                .addCode("startActivity(intent)")


        navClass.addType(companion.build())


        val file = File(generatedSourcesRoot)
        file.mkdir()
        FileSpec.builder(packageOfMethod, fileName)
                .addFunction(goToFuncBuilder.build())
                .addFunction(loadFuncBuilder.build())
                .addType(navClass.build()).build()
                .writeTo(file)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Parameter::class.java.canonicalName)
    }
}

private fun TypeName.isLateInit(): Boolean {
    val exclude = listOf(
            Int::class.asTypeName(),
            Long::class.asTypeName(),
            Float::class.asTypeName(),
            Boolean::class.asTypeName()
    )
    return this !in exclude && this !in exclude.map { it.asNullable() }
}

private fun Element.isNullable(): Boolean {
    return this.getAnnotation(org.jetbrains.annotations.Nullable::class.java) != null
}

fun Element.javaToKotlinType(nullable: Boolean = false): TypeName {
    return if (isNullable())
        asType().asTypeName().javaToKotlinType(nullable).asNullable()
    else
        asType().asTypeName().javaToKotlinType(nullable).asNonNullable()
}

private fun TypeName.javaToKotlinType(nullable: Boolean): TypeName {
    return if (this is ParameterizedTypeName) {
        ParameterizedTypeName.get(
                rawType.javaToKotlinType(nullable) as ClassName,
                *typeArguments.map {
                    if (nullable){
                        it.javaToKotlinType(nullable).asNullable()
                    } else {
                        it.javaToKotlinType(nullable).asNonNullable()
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
