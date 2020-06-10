package es.adrianmmudarra.hablave.data.repository

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

    fun reserveTrip(jsonObject: JsonObject, listener: OnConfirmTripInteract){
        val call: Call<Void> = service.addReserve(jsonObject)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(callback: Call<Void?>?, response: Response<Void?>) {
                if (response.code() == 202) {
                    listener.onSuccessReserve()
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

    fun cancelReserveTrip(jsonObject: JsonObject, listener: OnConfirmTripInteract){
        val call: Call<Void> = service.deleteReserve(jsonObject)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(callback: Call<Void?>?, response: Response<Void?>) {
                if (response.code() == 202) {
                    listener.onSuccessCancelReserve()
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