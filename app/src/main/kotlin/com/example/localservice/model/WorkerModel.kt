package com.example.localservice.model

data class WorkerModel(

    val uid: String = "",

    val id: String = "",

    val fullName: String = "",

    val email: String = "",

    val contactNumber: String = "",

    val gender: String = "",

    val dob: String = "",

    val serviceTypes: List<String> = emptyList(),

    val addrLine1: String = "",

    val addrLine2: String = "",

    val area: String = "",

    val city: String = "",

    val state: String = "",

    val pincode: String = "",

    val preferredAreasText: String = "",

    val profilePic: String = "",

    val idProof: String = "",

    val minRate: Int = 0,

    val isAvailable: Boolean = false,

    val rating: Double = 4.5,

    val totalRatings: Int = 0,

    val totalEarnings: Int = 0,

    val createdAt: String = "",

    val updatedAt: String = ""
)