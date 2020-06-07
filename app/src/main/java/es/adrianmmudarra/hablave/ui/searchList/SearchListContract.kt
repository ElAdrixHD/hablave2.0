package es.adrianmmudarra.hablave.ui.searchList

import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.base.BaseView

class SearchListContract {
    interface View: BaseView<Presenter>{
        fun onSuccessTripList(list: ArrayList<Trip>)
        fun onAddTrip(trip: Trip)
        fun onRemoveTrip(trip: Trip)
        fun noTrips()
        fun onUpdatedTrip(trip: Trip)
    }

    interface Presenter{
        fun loadTrips(origin: Station, destiny: Station, date: String)
    }
}