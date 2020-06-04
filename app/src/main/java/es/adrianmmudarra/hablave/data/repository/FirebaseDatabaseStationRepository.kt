package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import es.adrianmmudarra.hablave.data.model.Station

class FirebaseDatabaseStationRepository {

    interface CreateTripInteract{
        fun onSuccessGetStation(stations: ArrayList<Station>)
    }
    interface SearchTripInteract{
        fun onSuccessGetStation(stations: ArrayList<Station>)
    }

    private val database = FirebaseFirestore.getInstance()

    companion object{
        private var INSTANCE : FirebaseDatabaseStationRepository? = null

        fun getInstance(): FirebaseDatabaseStationRepository{
            if (INSTANCE == null){
                INSTANCE = FirebaseDatabaseStationRepository()
            }
            return INSTANCE as FirebaseDatabaseStationRepository
        }
    }

    fun getStations(createTripInteract: CreateTripInteract){
        val stations = ArrayList<Station>()
        database.collection("Station").get().addOnSuccessListener { documents ->
            for (document in documents) {
                stations.add(document.toObject(Station::class.java))
            }
            createTripInteract.onSuccessGetStation(stations)
        }
    }

    fun getStations(searchTripInteract: SearchTripInteract){
        val stations = ArrayList<Station>()
        database.collection("Station").get().addOnSuccessListener { documents ->
            for (document in documents) {
                stations.add(document.toObject(Station::class.java))
            }
            searchTripInteract.onSuccessGetStation(stations)
        }
    }
}