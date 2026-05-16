package com.example.localservice.repository

import com.example.localservice.firebase.FirebaseManager
import com.example.localservice.model.WorkerModel
import kotlinx.coroutines.tasks.await

class WorkerRepository {

    private val firestore = FirebaseManager.firestore

    suspend fun saveWorker(
        uid: String,
        worker: WorkerModel
    ) {

        firestore.collection("workers")
            .document(uid)
            .set(worker)
            .await()

        firestore.collection("worker_status")
            .document(uid)
            .set(
                mapOf(
                    "workerId" to uid,
                    "isAvailable" to false
                )
            )
            .await()
    }

    suspend fun getWorker(
        uid: String
    ): WorkerModel? {

        val snapshot = firestore
            .collection("workers")
            .document(uid)
            .get()
            .await()

        return snapshot.toObject(WorkerModel::class.java)
    }

    suspend fun workerExists(
        uid: String
    ): Boolean {

        val snapshot = firestore
            .collection("workers")
            .document(uid)
            .get()
            .await()

        return snapshot.exists()
    }

    suspend fun updateWorkerAvailability(
        uid: String,
        isAvailable: Boolean
    ) {

        firestore.collection("worker_status")
            .document(uid)
            .update(
                "isAvailable",
                isAvailable
            )
            .await()
    }

    suspend fun addEarning(
        workerUid: String,
        amount: Int,
        date: String,
        time: String
    ) {

        val earning = mapOf(
            "amount" to amount,
            "date" to date,
            "time" to time
        )

        firestore.collection("workers")
            .document(workerUid)
            .collection("earnings")
            .add(earning)
            .await()
    }

    suspend fun addRating(
        workerUid: String,
        residentId: String,
        score: Int
    ) {

        val rating = mapOf(
            "workerId" to workerUid,
            "residentId" to residentId,
            "score" to score
        )

        firestore.collection("worker_ratings")
            .add(rating)
            .await()
    }
}