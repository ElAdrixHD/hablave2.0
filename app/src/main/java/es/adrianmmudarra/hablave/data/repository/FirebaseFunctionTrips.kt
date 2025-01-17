package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import es.adrianmmudarra.hablave.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirebaseFunctionTrips {

    interface OnConfirmTripInteract{
        fun onSuccessReserve()
        fun onErrorReserve()
        fun onTripCompleted()

        fun onSuccessCancelReserve()
        fun onErrorCancelReserve()
    }

    companion object {
        private var INSTANCE: FirebaseFunctionTrips? = null

        fun getInstance(): FirebaseFunctionTrips {
            if (INSTANCE == null) {
                INSTANCE = FirebaseFunctionTrips()
            }
            return INSTANCE as FirebaseFunctionTrips
        }
    }

    private val service = RetrofitClient.getInstance()

    fun reserveTrip(jsonObject: JsonObject, uid: String, listener: OnConfirmTripInteract){
        val call: Call<Void> = service.addReserve(jsonObject)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(callback: Call<Void?>?, response: Response<Void?>) {
                if (response.code() == 202) {
                    listener.onSuccessReserve()
                    subscribeTopic(uid)
                }else{
                    if (response.code() == 403){
                        listener.onTripCompleted()
                    }else{
                        listener.onErrorReserve()
                    }

                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                listener.onErrorReserve()
            }
        })
    }

    private fun subscribeTopic(uid: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(uid)
    }

    private fun unsubscribeTopic(uid: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(uid)
    }

    fun cancelReserveTrip(jsonObject: JsonObject,uid:String, listener: OnConfirmTripInteract){
        val call: Call<Void> = service.deleteReserve(jsonObject)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(callback: Call<Void?>?, response: Response<Void?>) {
                if (response.code() == 202) {
                    listener.onSuccessCancelReserve()
                    unsubscribeTopic(uid)
                }else{
                    listener.onErrorCancelReserve()

                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                listener.onErrorCancelReserve()
            }
        })
    }
}