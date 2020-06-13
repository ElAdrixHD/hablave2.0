package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.base.BaseView

class ProfileHistoryContract {

    interface View : BaseView<Presenter>{
        fun addTrip(trip: Trip)
        fun noTrips()
    }

    interface Presenter {
        fun loadTrips()
    }
}