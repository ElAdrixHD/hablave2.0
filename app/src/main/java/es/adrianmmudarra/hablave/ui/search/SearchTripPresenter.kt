package es.adrianmmudarra.hablave.ui.search

import android.os.Bundle
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseStationRepository
import kotlinx.android.synthetic.main.fragment_search_trip_view.*

class SearchTripPresenter(private val view: SearchTripContract.View) : SearchTripContract.Presenter, FirebaseDatabaseStationRepository.SearchTripInteract{

    override fun loadStations() {
        FirebaseDatabaseStationRepository.getInstance().getStations(this)
    }

    override fun checkData(origin: Station?, destiny: Station?, date: String) {
        if (checkOrigin(origin) and checkDestiny(destiny) and checkDate(date)){
                if (checkStations(origin, destiny)) {
                    var bundle = Bundle().apply { putParcelable("ORIGIN", origin); putParcelable("DESTINY", destiny); putString("DATE", date) }
                    view.onSuccessCheck(bundle)
                }
        }
    }

    private fun checkStations(origin: Station?, destiny: Station?): Boolean {
        if (origin == destiny){
            view.onErrorStationOrigin(R.string.same_stations)
            view.onErrorStationDest(R.string.same_stations)
            return false
        }
        view.onClearStationOrigin()
        view.onClearStationDest()
        return true
    }

    private fun checkDate(date: String): Boolean {
        if (date.isNullOrEmpty()){
            view.onErrorDateTrip(R.string.date_trip_empty)
            return false
        }
        view.onClearDateTrip()
        return true
    }

    private fun checkDestiny(destiny: Station?): Boolean {
        if (destiny == null){
            view.onErrorStationDest(R.string.station_destiny_error)
            return false
        }
        view.onClearStationDest()
        return true
    }

    private fun checkOrigin(origin: Station?): Boolean {
        if (origin == null){
            view.onErrorStationOrigin(R.string.station_origin_error)
            return false
        }
        view.onClearStationOrigin()
        return true
    }

    override fun onSuccessGetStation(stations: ArrayList<Station>) {
        view.setStations(stations)
    }

}