package com.example.localservice.model

data class WorkerModel(

    // Basic Details
    val fullName: String = "",

    val contactNumber: String = "",

    val email: String = "",

    val gender: String = "",

    val dob: String = "",

    // Services
    val services: List<String> = emptyList(),

    // Address
    val addrLine1: String = "",

    val addrLine2: String = "",

    val area: String = "",

    val city: String = "",

    val state: String = "",

    val pincode: String = "",

    // Work Areas
    val workAreas: String = "",

    // Uploaded Images
    val profilePhotoUrl: String = "",

    val idCardPhotoUrl: String = "",

    // Worker System Data
    val workerId: String = "",

    val isAvailable: Boolean = false,

    val averageRating: Double = 0.0,

    val totalRatings: Int = 0,

    val totalEarnings: Int = 0,

    // Metadata
    val createdAt: Long = System.currentTimeMillis()
)