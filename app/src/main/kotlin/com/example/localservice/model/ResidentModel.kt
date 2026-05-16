package com.example.localservice.model

data class ResidentModel(
    val fullName: String = "",
    val email: String = "",
    val contactNumber: String = "",
    val addrLine1: String = "",
    val addrLine2: String = "",
    val area: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val createdAt: Long = System.currentTimeMillis()
)