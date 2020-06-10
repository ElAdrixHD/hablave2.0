package es.adrianmmudarra.hablave.ui.confirm

import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.model.User
import es.adrianmmudarra.hablave.ui.base.BaseView

class ConfirmTripContract {

    interface View: BaseView<Presenter>{
        fun updatedTrip(trip: Trip)
        fun deletedTrip()
        fun onSuccessReserve()
        fun onErrorReserve()
        fun onTripCompleted()
        fun onSuccessCancelReserve()
        fun onErrorCancelReserve()
    }

    interface Presenter{
        fun loadData(trip: Trip)
        fun deleteTrip(trip: Trip)
        fun reserveTrip(trip: Trip)
        fun cancelReserve(trip: Trip)
    }
}