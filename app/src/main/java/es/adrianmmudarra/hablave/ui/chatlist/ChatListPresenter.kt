package es.adrianmmudarra.hablave.ui.chatlist

import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripChatRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripRepository

class ChatListPresenter(val view: ChatListContract.View): ChatListContract.Presenter,
    FirebaseDatabaseTripRepository.OnChatListInteract {

    override fun loadChats() {
        FirebaseDatabaseTripRepository.getInstance().getFutureTrips(HablaveApplication.context.user?.uid!!, this)
    }

    override fun onSuccessAdd(trip: Trip) {
        view.onAddTrip(trip)
    }

    override fun noData() {
        view.noTrips()
    }
}