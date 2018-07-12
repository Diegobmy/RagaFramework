package com.example.ragabuza.raga_processor

import com.example.ragabuza.raga_annotation.SharedController
import com.example.ragabuza.raga_annotation.capitalizeFirst
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(SharedPreferenceProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class SharedPreferenceProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        val fileSpec = FileSpec.builder("com.example.ragabuza.baseragaapp", "SharedContractor")
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val generatedSourcesRoot: String = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
        val file = File(generatedSourcesRoot)
        file.mkdir()

        roundEnv.getElementsAnnotatedWith(SharedController::class.java).forEach { controller ->
            controller.enclosedElements.forEach { element ->
                if (element.kind == ElementKind.FIELD)
                    generateNewMethod(element, controller)
            }
        }

        fileSpec.build()
                .writeTo(file)

        return false
    }

    val presenterClass = ParameterizedTypeName.get(
            ClassName("com.example.ragabuza.baseragaapp.base", "BasePresenter"),
            TypeVariableName("T")
    )

    private fun generateNewMethod(parameter: Element, controller: Element) {

        val packageOfMethod = processingEnv.elementUtils.getPackageOf(controller).toString()
        val controllerName = controller.simpleName.toString()
        val controllerClass = ClassName(packageOfMethod, controllerName)

        val parameterType = parameter.javaToKotlinType()
        val parameterName = parameter.simpleName.toString()

        val funcGet = FunSpec.builder("save${parameterName.capitalizeFirst()}")
                .addModifiers(KModifier.PUBLIC)
                .receiver(presenterClass)
                .addParameter(parameterName, parameterType)
                .addTypeVariable(TypeVariableName("T").withBounds(ClassName("com.example.ragabuza.baseragaapp.base", "BaseActivity")))
                .buildGetBody(parameterType, parameterName)

        val funcSet = FunSpec.builder("load${parameterName.capitalizeFirst()}")
                .addModifiers(KModifier.PUBLIC)
                .receiver(presenterClass)
                .addTypeVariable(TypeVariableName("T").withBounds(ClassName("com.example.ragabuza.baseragaapp.base", "BaseActivity")))
                .returns(parameterType)
                .buildSetBody(parameterType, parameterName, controllerClass)

        fileSpec.addFunction(funcGet.build())
        fileSpec.addFunction(funcSet.build())

    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(SharedController::class.java.canonicalName)
    }

    private fun FunSpec.Builder.buildGetBody(parameterType: TypeName, parameterName: String): FunSpec.Builder {
        addStatement("val editor = shared.edit()")
        when (parameterType) {
            Boolean::class.asTypeName() -> {
                addStatement("editor.putBoolean(\"$parameterName\",$parameterName)")
            }
            Float::class.asTypeName() -> {
                addStatement("editor.putFloat(\"$parameterName\",$parameterName)")
            }
            Int::class.asTypeName() -> {
                addStatement("editor.putInt(\"$parameterName\",$parameterName)")
            }
            Long::class.asTypeName() -> {
                addStatement("editor.putLong(\"$parameterName\",$parameterName)")
            }
            String::class.asTypeName() -> {
                addStatement("editor.putString(\"$parameterName\",$parameterName)")
            }
            else -> {
                addStatement("val gson = %T()", ClassName("com.google.gson", "Gson"))
                addStatement("editor.putString(\"$parameterName\",gson.toJson($parameterName))")
            }
        }
        addStatement("editor.apply()")
        return this
    }

    private fun FunSpec.Builder.buildSetBody(parameterType: TypeName, parameterName: String, controllerClass: ClassName): FunSpec.Builder {
        when (parameterType) {
            Boolean::class.asTypeName() -> {
                addStatement("val result = shared.getBoolean(\"$parameterName\", %T().$parameterName)", controllerClass)
            }
            Float::class.asTypeName() -> {
                addStatement("val result = shared.getFloat(\"$parameterName\", %T().$parameterName)", controllerClass)
            }
            Int::class.asTypeName() -> {
                addStatement("val result = shared.getInt(\"$parameterName\", %T().$parameterName)", controllerClass)
            }
            Long::class.asTypeName() -> {
                addStatement("val result = shared.getLong(\"$parameterName\", %T().$parameterName)", controllerClass)
            }
            String::class.asTypeName() -> {
                addStatement("val result = shared.getString(\"$parameterName\", %T().$parameterName)", controllerClass)
            }
            else -> {
                addStatement("val gson = %T()", ClassName("com.google.gson", "Gson"))
                addStatement("var result = gson.fromJson(shared.getString(\"$parameterName\", \"\"), $parameterType::class.java)")
                addStatement("if (result == null) result = %T().$parameterName", controllerClass)
            }
        }
        addStatement("return result")
        return this
    }
}
