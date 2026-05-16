package com.example.localservice.data

import com.example.localservice.firebase.FirebaseManager
import com.google.firebase.firestore.DocumentSnapshot

class FirestoreRepository {

    private val firestore = FirebaseManager.firestore

    fun checkResidentExists(
        uid: String,
        onResult: (Boolean) -> Unit
    ) {

        firestore.collection("residents")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                onResult(document.exists())
            }
            .addOnFailureListener {

                onResult(false)
            }
    }

    fun checkWorkerExists(
        uid: String,
        onResult: (Boolean) -> Unit
    ) {

        firestore.collection("workers")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                onResult(document.exists())
            }
            .addOnFailureListener {

                onResult(false)
            }
    }
}