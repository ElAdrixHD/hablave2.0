package es.adrianmmudarra.hablave.ui.confirm

import com.google.gson.JsonObject
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseFunctionTrips
import org.json.JSONObject

class ConfirmTripPresenter(val view: ConfirmTripContract.View): ConfirmTripContract.Presenter, FirebaseDatabaseTripRepository.OnConfrimTripInteract, FirebaseFunctionTrips.OnConfirmTripInteract{

    override fun loadData(trip: Trip) {
        FirebaseDatabaseTripRepository.getInstance().searchTripByUUID(trip.uuid,this)
    }

    override fun deleteTrip(trip: Trip) {
        FirebaseDatabaseTripRepository.getInstance().deleteTrip(trip,this)
    }

    override fun reserveTrip(trip: Trip) {
        val jsonObject = JsonObject().apply {
            addProperty("uidTrip", trip.uuid)
            addProperty("idUser", HablaveApplication.context.user?.uid!!)
            addProperty("nameUser", HablaveApplication.context.user?.nameAndSurname!!)
        }
        FirebaseFunctionTrips.getInstance().reserveTrip(jsonObject,this)
    }

    override fun cancelReserve(trip: Trip) {
        val jsonObject = JsonObject().apply {
            addProperty("uidTrip", trip.uuid)
            addProperty("idUser", HablaveApplication.context.user?.uid!!)
            addProperty("nameUser", HablaveApplication.context.user?.nameAndSurname!!)
        }
        FirebaseFunctionTrips.getInstance().cancelReserveTrip(jsonObject,this)
    }

    override fun onUpdatedTrip(trip: Trip) {
        view.updatedTrip(trip)
    }

    override fun onDeletedTrip() {
        view.deletedTrip()
    }

    override fun onSuccessReserve() {
        view.onSuccessReserve()
    }

    override fun onErrorReserve() {
        view.onErrorReserve()
    }

    override fun onTripCompleted() {
        view.onTripCompleted()
    }

    override fun onSuccessCancelReserve() {
        view.onSuccessCancelReserve()
    }

    override fun onErrorCancelReserve() {
        view.onErrorCancelReserve()
    }
}