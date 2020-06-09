package es.adrianmmudarra.hablave.network

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST

interface FirebaseFunctionHTTP {

    @Headers("Accept: application/json","Content-Type: application/json", "Connection: keep-alive", "User-Agent: Hablave")

    @POST("AddUserToTrip/")
    fun addReserve(@Body jsonObject: JsonObject): Call<String>

    @DELETE("AddUserToTrip/")
    fun deleteReserve(@Body jsonObject: JSONObject): Call<String>
}