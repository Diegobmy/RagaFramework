package com.example.ragabuza.raga_processor

import com.example.ragabuza.raga_annotation.Parameter
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import kotlin.reflect.jvm.internal.impl.name.FqName
import kotlin.reflect.jvm.internal.impl.platform.JavaToKotlinClassMap

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(NewIntentProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class NewIntentProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        val fileSpec = FileSpec.builder("com.example.ragabuza.baseragaapp", "Navigator")
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val generatedSourcesRoot: String = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
        val file = File(generatedSourcesRoot)
        file.mkdir()

        roundEnv.getElementsAnnotatedWith(Parameter::class.java)
                .forEach {
                    generateNewMethod(it.enclosingElement, it.enclosedElements)
                }


        fileSpec.build()
                .writeTo(file)

        return false
    }

    private fun generateNewMethod(activity: Element, parameters: MutableList<out Element>) {
        val packageOfMethod = processingEnv.elementUtils.getPackageOf(activity).toString()

        val activityName = activity.simpleName.toString()
        val activityClass = ClassName(packageOfMethod, activityName)

        val goToFuncBuilder = FunSpec.builder("goTo$activityName")
                .addModifiers(KModifier.PUBLIC)
                .receiver(ClassName("android.content", "Context"))

        parameters.filter { it.kind == ElementKind.METHOD && (it as ExecutableElement).parameters.isNotEmpty() }.forEach {
            val parameter = (it as ExecutableElement).parameters[0]
            val parameterName = it.getParameterName()
            goToFuncBuilder.addParameter(parameterName, parameter.javaToKotlinType())

            goToFuncBuilder.addStatement("%T.$parameterName = $parameterName\n", activityClass)
        }

        goToFuncBuilder.addStatement("val intent = %T(this, %T::class.java)",
                ClassName("android.content", "Intent"),
                activityClass)
                .addCode("startActivity(intent)")

        fileSpec.addFunction(goToFuncBuilder.build())

    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Parameter::class.java.canonicalName)
    }
}