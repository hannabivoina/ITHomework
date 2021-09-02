package com.example.contactbook.model

import java.io.Serializable

data class Contact(
    val id: Int,
    val name: String,
    val info: String,
    val type: String
) : Serializable {
    override fun toString(): String {
        return name
    }
}