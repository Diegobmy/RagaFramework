package com.example.ragabuza.raga_processor

import com.example.ragabuza.raga_annotation.Types
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.ExecutableType

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(TypesConstructorProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class TypesConstructorProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        val fileSpec = FileSpec.builder("com.example.ragabuza.baseragaapp", "TypesCreator")
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val generatedSourcesRoot: String = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
        val file = File(generatedSourcesRoot)
        file.mkdir()

        roundEnv.getElementsAnnotatedWith(Types::class.java)
                .forEach {
                    generateNewMethod(it.enclosingElement, it)
                }


        fileSpec.build()
                .writeTo(file)

        return false
    }

    private fun generateNewMethod(activity: Element, enum: Element) {
        val packageOfMethod = processingEnv.elementUtils.getPackageOf(activity).toString()

        val activityName = activity.simpleName.toString()
        val activityClass = ClassName(packageOfMethod, activityName)
        var parameters = (activity.enclosedElements
                .find { it.kind == ElementKind.CONSTRUCTOR } as ExecutableElement)
                .parameters

        enum.enclosedElements.filter { it.kind == ElementKind.ENUM_CONSTANT }.forEach { enumConstant ->
            val createNew = FunSpec.builder("${enumConstant.simpleName}$activityName")
                    .addModifiers(KModifier.PUBLIC)

            val returnString = StringBuilder("return %T(")

            parameters.forEach { parameter ->
                if (parameter.javaToKotlinType() != enum.javaToKotlinType()) {
                    createNew.addParameter(parameter.toString(), parameter.javaToKotlinType())
                    returnString.append("$parameter, ")
                } else {
                    returnString.append("%T.$enumConstant, ")
                }
            }

            returnString.replace(returnString.length-2, returnString.length, ")")

            createNew.addStatement(returnString.toString(), activityClass, enum.javaToKotlinType())

            fileSpec.addFunction(createNew.build())
        }

    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Types::class.java.canonicalName)
    }
}