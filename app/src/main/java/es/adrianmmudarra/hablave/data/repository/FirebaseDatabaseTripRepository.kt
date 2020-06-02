package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import es.adrianmmudarra.hablave.data.model.Trip

class FirebaseDatabaseTripRepository {

    private val database = FirebaseFirestore.getInstance()


    interface OnCreateTripInteract{
        fun onSuccessCreateTrip()
        fun onFailureCreateTrip()
    }
    companion object{
        private var INSTANCE: FirebaseDatabaseTripRepository? = null

        fun getInstance(): FirebaseDatabaseTripRepository{
            if (INSTANCE == null){
                INSTANCE = FirebaseDatabaseTripRepository()
            }
            return INSTANCE as FirebaseDatabaseTripRepository
        }
    }

    fun addTrip(trip: Trip, listener: OnCreateTripInteract){
        database.collection("Trip").document(trip.uuid).set(trip).addOnSuccessListener {
            listener.onSuccessCreateTrip()
        }.addOnFailureListener {
            listener.onFailureCreateTrip()
        }
    }
}