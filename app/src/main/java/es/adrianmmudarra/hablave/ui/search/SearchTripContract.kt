package es.adrianmmudarra.hablave.ui.search

import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.ui.base.BaseView

class SearchTripContract {
    interface View : BaseView<Presenter>{
        fun onErrorStationOrigin(error:Int)
        fun onClearStationOrigin()
        fun onErrorStationDest(error:Int)
        fun onClearStationDest()
        fun onErrorDateTrip(error:Int)
        fun onClearDateTrip()
        fun setStations(station: ArrayList<Station>)
    }
    interface Presenter{
        fun loadStations()
    }
}