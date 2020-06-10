package es.adrianmmudarra.hablave.network

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface FirebaseFunctionHTTP {

    @Headers("Accept: application/json","Content-Type: application/json", "Connection: keep-alive", "User-Agent: Hablave")

    @POST("AddUserToTrip/")
    fun addReserve(@Body jsonObject: JsonObject): Call<Void>

    @HTTP(method = "DELETE", path = "AddUserToTrip/", hasBody = true)
    fun deleteReserve(@Body jsonObject: JsonObject): Call<Void>
}