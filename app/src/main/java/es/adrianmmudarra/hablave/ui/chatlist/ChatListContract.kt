package es.adrianmmudarra.hablave.ui.chatlist

import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.base.BaseView

class ChatListContract {
    interface View : BaseView<Presenter>{
        fun onSuccessTripList(list: ArrayList<Trip>)
        fun onAddTrip(trip: Trip)
        fun noTrips()
    }

    interface Presenter{
        fun loadChats()
    }
}