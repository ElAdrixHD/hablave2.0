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
        val call: Call<String> = service.addReserve(jsonObject)
        call.enqueue(object : Callback<String?> {
            override fun onResponse(callback: Call<String?>?, response: Response<String?>) {
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

            override fun onFailure(call: Call<String?>, t: Throwable) {
                listener.onErrorReserve()
            }
        })
    }
}