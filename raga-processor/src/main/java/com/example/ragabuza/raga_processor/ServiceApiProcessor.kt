package com.example.ragabuza.raga_processor

import com.example.ragabuza.raga_annotation.Service
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(ServiceApiProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class ServiceApiProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        val fileSpec = FileSpec.builder("com.example.ragabuza.baseragaapp", "ServicesController")
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val generatedSourcesRoot: String = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
        val file = File(generatedSourcesRoot)
        file.mkdir()

        roundEnv.getElementsAnnotatedWith(Service::class.java)
                .forEach {
                    generateNewMethod(it)
                }


        fileSpec.build()
                .writeTo(file)

        return false
    }

    val presenterClass = ParameterizedTypeName.get(
            ClassName("com.example.ragabuza.baseragaapp.base", "BasePresenter"),
            TypeVariableName("T")
    )

    val retrofitClass = ClassName("retrofit2", "Retrofit")
    val rxAdapterClass = ClassName("retrofit2.adapter.rxjava2", "RxJava2CallAdapterFactory")
    val gsonFactoryClass = ClassName("retrofit2.converter.gson", "GsonConverterFactory")

    private fun generateNewMethod(service: Element) {
        val packageOfMethod = processingEnv.elementUtils.getPackageOf(service).toString()
        val serviceName = service.simpleName.toString()
        val serviceClass = ClassName(packageOfMethod, serviceName)

        val createFunction = FunSpec.builder("get$serviceName")
                .addModifiers(KModifier.PUBLIC)
                .receiver(presenterClass)
                .addTypeVariable(TypeVariableName("T").withBounds(ClassName("com.example.ragabuza.baseragaapp.base", "BaseActivity")))
                .returns(service.asType().asTypeName())
                .addStatement("if (cacheServices[\"$serviceName\"] == null) {")
                .addStatement("val retrofit = %T.Builder()", retrofitClass)
                .addStatement(".addCallAdapterFactory(%T.create())", rxAdapterClass)
                .addStatement(".addConverterFactory(%T.create())", gsonFactoryClass)
                .addStatement(".baseUrl(\"${service.getAnnotation(Service::class.java).baseUrl}\")")
                .addStatement(".build()")
                .addStatement("cacheServices[\"$serviceName\"] = retrofit.create(%T::class.java) }", serviceClass)
                .addStatement("return cacheServices[\"$serviceName\"] as %T", serviceClass)

        fileSpec.addFunction(createFunction.build())

    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Service::class.java.canonicalName)
    }
}