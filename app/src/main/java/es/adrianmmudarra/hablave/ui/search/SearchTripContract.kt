package es.adrianmmudarra.hablave.ui.search

import android.os.Bundle
import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.ui.base.BaseView
import java.util.*
import kotlin.collections.ArrayList

class SearchTripContract {
    interface View : BaseView<Presenter>{
        fun onErrorStationOrigin(error:Int)
        fun onClearStationOrigin()
        fun onErrorStationDest(error:Int)
        fun onClearStationDest()
        fun onErrorDateTrip(error:Int)
        fun onClearDateTrip()
        fun setStations(station: ArrayList<Station>)
        fun onSuccessCheck(bundle: Bundle)
    }
    interface Presenter{
        fun loadStations()
        fun checkData(origin: Station?, destiny: Station?, date: String)
    }
}