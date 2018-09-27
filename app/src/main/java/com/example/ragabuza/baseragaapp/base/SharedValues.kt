package com.example.ragabuza.baseragaapp.base

import com.example.ragabuza.raga_annotation.SharedController
import java.util.*

data class Plumpinha(val aa: String);

@SharedController class SharedValues {
    val plumpinha: Plumpinha = Plumpinha("bjbjbjbj")
}