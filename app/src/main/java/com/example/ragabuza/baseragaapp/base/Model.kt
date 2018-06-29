package com.example.ragabuza.baseragaapp.base

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity data class Example(
        @Id var id : Long = 0,
        var name: String,
        var type: String
)