package es.adrianmmudarra.hablave.ui.create

import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseStationRepository

class CreateTripPresenter(val view: CreateTripContract.View): CreateTripContract.Presenter, FirebaseDatabaseStationRepository.CreateTripInteract {
    override fun getStations() {
        FirebaseDatabaseStationRepository.getInstance().getStations(this)
    }

    override fun onSuccessGetStation(stations: ArrayList<Station>) {
        view.setStations(stations)
    }
}