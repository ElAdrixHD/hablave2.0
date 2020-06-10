package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.confirm.ConfirmTripPresenter
import es.adrianmmudarra.hablave.utils.dateToLong

class FirebaseDatabaseTripRepository {

    private val database = FirebaseFirestore.getInstance()


    interface OnCreateTripInteract{
        fun onSuccessCreateTrip(trip: Trip)
        fun onFailureCreateTrip()
    }

    interface OnSearchListInteract{
        fun onSuccessSearchTrip(trip: Trip)
        fun onDeletedTrip(trip: Trip)
        fun onUpdateTrip(trip: Trip)
        fun noData()
    }

    interface OnConfrimTripInteract{
        fun onUpdatedTrip(trip: Trip)
        fun onDeletedTrip()
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
            listener.onSuccessCreateTrip(trip)
        }.addOnFailureListener {
            listener.onFailureCreateTrip()
        }
    }

    fun searchTrips(station: String, station1: String, date: String, listInteract: OnSearchListInteract) {
        database.collection("Trip")
            .whereEqualTo("stationOrigin", station)
            .whereEqualTo("stationDest", station1)
            .whereEqualTo("dateTrip", date.dateToLong())
        .addSnapshotListener { querySnapshot, exception ->
            if (exception != null){
                return@addSnapshotListener
            }

            if (querySnapshot!!.documentChanges.count() == 0){
                listInteract.noData()
            }

            for (dc in querySnapshot!!.documentChanges) {
                when(dc.type){
                    DocumentChange.Type.ADDED -> listInteract.onSuccessSearchTrip(dc.document.toObject(Trip::class.java))
                    DocumentChange.Type.MODIFIED -> listInteract.onUpdateTrip(dc.document.toObject(Trip::class.java))
                    DocumentChange.Type.REMOVED -> listInteract.onDeletedTrip(dc.document.toObject(Trip::class.java));
                }
            }

        }
    }

    fun searchTripByUUID(uuid: String, onConfirmTripInteract: OnConfrimTripInteract) {
        database.collection("Trip")
            .whereEqualTo("uuid", uuid)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null){
                    return@addSnapshotListener
                }

                for (dc in querySnapshot!!.documentChanges) {
                    when(dc.type){
                        DocumentChange.Type.MODIFIED -> onConfirmTripInteract.onUpdatedTrip(dc.document.toObject(Trip::class.java))
                        DocumentChange.Type.REMOVED ->{
                            onConfirmTripInteract.onDeletedTrip();
                            return@addSnapshotListener
                        }
                        DocumentChange.Type.ADDED -> onConfirmTripInteract.onUpdatedTrip(dc.document.toObject(Trip::class.java))
                    }
                }

            }
    }

    fun deleteTrip(trip: Trip, confirmTripPresenter: OnConfrimTripInteract) {
        database.collection("Trip").document(trip.uuid).collection("Messages").get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
            }
        }
        database.collection("Trip").document(trip.uuid).delete()

    }
}