package com.example.ragabuza.raga_processor

import com.example.ragabuza.raga_annotation.Preference
import com.example.ragabuza.raga_annotation.splitLists
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(SharedPreferenceProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class SharedPreferenceProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        val fileSpec = FileSpec.builder("com.example.ragabuza.baseragaapp", "SharedLoader")
                .addFunction(FunSpec.builder("loadShared")
                .addModifiers(KModifier.PUBLIC)
                .receiver(ClassName("com.example.ragabuza.baseragaapp.base", "BaseActivity")).build())
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val generatedSourcesRoot: String = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
        val file = File(generatedSourcesRoot)
        file.mkdir()

        val map = roundEnv.getElementsAnnotatedWith(Preference::class.java).splitLists { it.enclosingElement }

        map.forEach {
            generateNewMethod(it.key as Element, it.value)
        }

        fileSpec.build()
                .writeTo(file)

        return false
    }

    private fun generateNewMethod(activity: Element, elements: MutableList<Element>) {

        val packageOfMethod = processingEnv.elementUtils.getPackageOf(activity).toString()
        val activityName = activity.simpleName.toString()
        val activityClass = ClassName(packageOfMethod, activityName)

        val funcSpec = FunSpec.builder("loadShared")
                .addModifiers(KModifier.PUBLIC)
                .receiver(activityClass)

        elements.forEach {

            var key = it.getAnnotation(Preference::class.java).key
            if (key.isEmpty()) key = it.simpleName.toString()

            funcSpec.addStatement("${it.simpleName} = shared.all[\"$key\"] as %T", it.javaToKotlinType())
        }

        fileSpec.addFunction(funcSpec.build())


    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Preference::class.java.canonicalName)
    }
}