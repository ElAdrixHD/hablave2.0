package es.adrianmmudarra.hablave.ui.searchList

import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripRepository

class SearchListPresenter(val view: SearchListContract.View): SearchListContract.Presenter, FirebaseDatabaseTripRepository.OnSearchListInteract {
    override fun loadTrips(origin: Station, destiny: Station, date: String) {
        FirebaseDatabaseTripRepository.getInstance().searchTrips(origin.station, destiny.station, date, this)
    }

    override fun onSuccessSearchTrip(trip: Trip) {
        view.onAddTrip(trip)
    }

    override fun onDeletedTrip(trip: Trip) {
        view.onRemoveTrip(trip)
    }

    override fun onUpdateTrip(trip: Trip) {
        view.onUpdatedTrip(trip)
    }

    override fun noData() {
        view.noTrips()
    }
}