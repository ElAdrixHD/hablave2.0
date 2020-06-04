package es.adrianmmudarra.hablave.ui.search

import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseStationRepository

class SearchTripPresenter(private val view: SearchTripContract.View) : SearchTripContract.Presenter, FirebaseDatabaseStationRepository.SearchTripInteract{

    override fun loadStations() {
        FirebaseDatabaseStationRepository.getInstance().getStations(this)
    }

    override fun onSuccessGetStation(stations: ArrayList<Station>) {
        view.setStations(stations)
    }

}