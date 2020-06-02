package es.adrianmmudarra.hablave.ui.create

import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.ui.base.BaseView

class CreateTripContract {

    interface View: BaseView<Presenter>{
        fun onErrorStationOrigin(error:Int)
        fun onClearStationOrigin()
        fun onErrorStationDest(error:Int)
        fun onClearStationDest()
        fun onErrorDateTrip(error:Int)
        fun onClearDateTrip()
        fun onErrorPriceTrip(error:Int)
        fun onClearPriceTrip()

        fun setStations(stations: ArrayList<Station>)
    }

    interface Presenter{
        fun getStations()
        fun createTrip(stationOrigin: Station, stationDest: Station, date: String, price: String, hasTicket: Boolean)
    }
}