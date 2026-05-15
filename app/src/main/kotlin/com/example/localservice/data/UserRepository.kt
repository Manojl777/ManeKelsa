package com.example.localservice.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val FIRESTORE_DATABASE_ID = "ai-studio-a414b521-3961-490f-adbb-7de6a70f1a77"

private fun db() = Firebase.firestore(FIRESTORE_DATABASE_ID)

data class UserProfile(
    val uid: String,
    val email: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val role: String? = null,
    val profileComplete: Boolean = false,
    val fullName: String? = null,
    val phone: String? = null,
    val gender: String? = null,
    val dateOfBirth: String? = null,
    val services: List<String> = emptyList(),
    val addressLine1: String? = null,
    val addressLine2: String? = null,
    val area: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pincode: String? = null,
    val workAreas: String? = null,
    val workerId: String? = null,
    val isOnline: Boolean = false,
    val minRate: String? = null,
    val totalEarnings: Int = 0,
    val ratingSum: Int = 0,
    val ratingCount: Int = 0,
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "uid" to uid,
        "email" to email,
        "displayName" to displayName,
        "photoUrl" to photoUrl,
        "role" to role,
        "profileComplete" to profileComplete,
        "fullName" to fullName,
        "phone" to phone,
        "gender" to gender,
        "dateOfBirth" to dateOfBirth,
        "services" to services,
        "addressLine1" to addressLine1,
        "addressLine2" to addressLine2,
        "area" to area,
        "city" to city,
        "state" to state,
        "pincode" to pincode,
        "workAreas" to workAreas,
        "workerId" to workerId,
        "isOnline" to isOnline,
        "minRate" to minRate,
        "totalEarnings" to totalEarnings,
        "ratingSum" to ratingSum,
        "ratingCount" to ratingCount,
    )

    companion object {
        fun fromDocument(uid: String, data: Map<String, Any?>): UserProfile {
            @Suppress("UNCHECKED_CAST")
            val services = (data["services"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
            return UserProfile(
                uid = uid,
                email = data["email"] as? String,
                displayName = data["displayName"] as? String,
                photoUrl = data["photoUrl"] as? String,
                role = data["role"] as? String,
                profileComplete = data["profileComplete"] as? Boolean ?: false,
                fullName = data["fullName"] as? String,
                phone = data["phone"] as? String,
                gender = data["gender"] as? String,
                dateOfBirth = data["dateOfBirth"] as? String,
                services = services,
                addressLine1 = data["addressLine1"] as? String,
                addressLine2 = data["addressLine2"] as? String,
                area = data["area"] as? String,
                city = data["city"] as? String,
                state = data["state"] as? String,
                pincode = data["pincode"] as? String,
                workAreas = data["workAreas"] as? String,
                workerId = data["workerId"] as? String,
                isOnline = data["isOnline"] as? Boolean ?: false,
                minRate = data["minRate"] as? String,
                totalEarnings = (data["totalEarnings"] as? Number)?.toInt() ?: 0,
                ratingSum = (data["ratingSum"] as? Number)?.toInt() ?: 0,
                ratingCount = (data["ratingCount"] as? Number)?.toInt() ?: 0,
            )
        }
    }
}

class UserRepository {

    suspend fun getUserProfile(uid: String): UserProfile? {
        val snap = db().collection("users").document(uid).get().await()
        if (!snap.exists()) return null
        val data = snap.data ?: return null
        return UserProfile.fromDocument(uid, data)
    }

    suspend fun saveAfterRoleSelection(uid: String, role: String, email: String?, displayName: String?, photoUrl: String?) {
        val map = mapOf(
            "uid" to uid,
            "email" to email,
            "displayName" to displayName,
            "photoUrl" to photoUrl,
            "role" to role,
            "profileComplete" to false,
        )
        db().collection("users").document(uid).set(map, SetOptions.merge()).await()
    }

    suspend fun completeResidentProfile(
        uid: String, 
        fullName: String, 
        phone: String, 
        email: String,
        addressLine1: String,
        addressLine2: String,
        area: String,
        city: String,
        state: String,
        pincode: String
    ) {
        val map = mapOf(
            "uid" to uid,
            "fullName" to fullName,
            "phone" to phone,
            "email" to email,
            "addressLine1" to addressLine1,
            "addressLine2" to addressLine2,
            "area" to area,
            "city" to city,
            "state" to state,
            "pincode" to pincode,
            "role" to "resident",
            "profileComplete" to true,
        )
        db().collection("users").document(uid).set(map, SetOptions.merge()).await()
    }

    suspend fun completeWorkerProfile(
        uid: String,
        fullName: String,
        phone: String,
        gender: String,
        dateOfBirth: String,
        services: List<String>,
        addressLine1: String,
        city: String,
        state: String,
        pincode: String,
        workAreas: String,
    ) {
        val map = mapOf(
            "uid" to uid,
            "fullName" to fullName,
            "phone" to phone,
            "gender" to gender,
            "dateOfBirth" to dateOfBirth,
            "services" to services,
            "addressLine1" to addressLine1,
            "city" to city,
            "state" to state,
            "pincode" to pincode,
            "workAreas" to workAreas,
            "role" to "worker",
            "profileComplete" to true,
            "workerId" to "MKW-${uid.take(6).uppercase()}",
            "isOnline" to false,
            "totalEarnings" to 0,
            "ratingSum" to 0,
            "ratingCount" to 0,
        )
        db().collection("users").document(uid).set(map, SetOptions.merge()).await()
    }

    suspend fun updateWorkerStatus(uid: String, isOnline: Boolean, minRate: String?) {
        val map = mapOf(
            "isOnline" to isOnline,
            "minRate" to minRate
        )
        db().collection("users").document(uid).set(map, SetOptions.merge()).await()
    }

    suspend fun addEarnings(uid: String, amount: Int) {
        val docRef = db().collection("users").document(uid)
        db().runTransaction { transaction ->
            val snap = transaction.get(docRef)
            val current = (snap.data?.get("totalEarnings") as? Number)?.toInt() ?: 0
            transaction.update(docRef, "totalEarnings", current + amount)
        }.await()
    }

    suspend fun addWorkerRating(workerUid: String, rating: Int) {
        val docRef = db().collection("users").document(workerUid)
        db().runTransaction { transaction ->
            val snap = transaction.get(docRef)
            val currentSum = (snap.data?.get("ratingSum") as? Number)?.toInt() ?: 0
            val currentCount = (snap.data?.get("ratingCount") as? Number)?.toInt() ?: 0
            transaction.update(docRef, "ratingSum", currentSum + rating)
            transaction.update(docRef, "ratingCount", currentCount + 1)
        }.await()
    }

    fun signOutFirebase() {
        Firebase.auth.signOut()
    }
}
