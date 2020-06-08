package es.adrianmmudarra.hablave.ui.confirm

import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripRepository

class ConfirmTripPresenter(val view: ConfirmTripContract.View): ConfirmTripContract.Presenter, FirebaseDatabaseTripRepository.OnConfrimTripInteract{

    override fun loadData(trip: Trip) {
        FirebaseDatabaseTripRepository.getInstance().searchTripByUUID(trip.uuid,this)
    }

    override fun deleteTrip(trip: Trip) {
        FirebaseDatabaseTripRepository.getInstance().deleteTrip(trip,this)
    }

    override fun reserveTrip(trip: Trip) {
        TODO("Not yet implemented")
    }

    override fun onUpdatedTrip(trip: Trip) {
        view.updatedTrip(trip)
    }

    override fun onDeletedTrip() {
        view.deletedTrip()
    }
}