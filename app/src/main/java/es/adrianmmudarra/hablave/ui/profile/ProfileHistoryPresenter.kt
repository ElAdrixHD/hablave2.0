package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripRepository

class ProfileHistoryPresenter(val view: ProfileHistoryContract.View): ProfileHistoryContract.Presenter, FirebaseDatabaseTripRepository.OnProfileHistoryInteract {
    override fun loadTrips() {
        FirebaseDatabaseTripRepository.getInstance().getPastTrips(HablaveApplication.context.user?.uid!!, this)
    }

    override fun onSuccessAdd(trip: Trip) {
        view.addTrip(trip)
    }

    override fun noData() {
        view.noTrips()
    }
}