package com.example.localservice.repository

import com.example.localservice.firebase.FirebaseManager
import com.example.localservice.model.ResidentModel
import kotlinx.coroutines.tasks.await

class ResidentRepository {

    private val firestore = FirebaseManager.firestore

    suspend fun saveResident(
        uid: String,
        resident: ResidentModel
    ) {

        firestore.collection("residents")
            .document(uid)
            .set(resident)
            .await()
    }

    suspend fun getResident(
        uid: String
    ): ResidentModel? {

        val snapshot = firestore
            .collection("residents")
            .document(uid)
            .get()
            .await()

        return snapshot.toObject(ResidentModel::class.java)
    }

    suspend fun residentExists(
        uid: String
    ): Boolean {

        val snapshot = firestore
            .collection("residents")
            .document(uid)
            .get()
            .await()

        return snapshot.exists()
    }
}