package es.adrianmmudarra.hablave.ui.create

import com.google.firebase.auth.FirebaseAuth
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseStationRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripRepository
import es.adrianmmudarra.hablave.utils.dateToLong
import java.util.*
import kotlin.collections.ArrayList

class CreateTripPresenter(val view: CreateTripContract.View): CreateTripContract.Presenter, FirebaseDatabaseStationRepository.CreateTripInteract, FirebaseDatabaseTripRepository.OnCreateTripInteract {
    override fun getStations() {
        FirebaseDatabaseStationRepository.getInstance().getStations(this)
    }

    override fun createTrip(
        stationOrigin: Station,
        stationDest: Station,
        date: String,
        price: String,
        hasTicket: Boolean
    ) {
        if (checkStations(stationOrigin, stationDest) and checkPrice(price) and checkDate(date)){
            FirebaseDatabaseTripRepository.getInstance().addTrip(Trip().apply {
                this.stationOrigin = stationOrigin.station
                this.stationDest = stationDest.station
                this.dateTrip = date.dateToLong()
                this.price = price.toDouble()
                this.hasTicket = hasTicket
                this.owner = FirebaseAuth.getInstance().currentUser?.uid!!
                this.uuid = "${this.owner}-${this.dateTrip}"},this)
        }
    }

    private fun checkDate(date: String): Boolean {
        if (date.isEmpty()){
            view.onErrorDateTrip(R.string.date_trip_empty)
            return false
        }
        view.onClearDateTrip()
        return true
    }

    private fun checkPrice(price: String): Boolean {
        if (price.isEmpty()){
            view.onErrorPriceTrip(R.string.emptyPrice)
            return false
        }
        if (price.toDouble() <= 0){
            view.onErrorPriceTrip(R.string.invalid_price_format)
            return false
        }
        view.onClearPriceTrip()
        return true
    }

    private fun checkStations(stationOrigin: Station, stationDest: Station): Boolean {
        if (stationOrigin == stationDest){
            view.onErrorStationOrigin(R.string.same_stations)
            view.onErrorStationDest(R.string.same_stations)
            return false
        }
        view.onClearStationOrigin()
        view.onClearStationDest()
        return true
    }

    override fun onSuccessGetStation(stations: ArrayList<Station>) {
        view.setStations(stations)
    }

    override fun onSuccessCreateTrip() {
        view.onSnakbarError("Se ha creado un viaje")
    }

    override fun onFailureCreateTrip() {
        view.onSnakbarError("Solo puedes un viaje por día")
    }
}