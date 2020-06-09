package es.adrianmmudarra.hablave.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        private var retrofitClient: Retrofit? = null

        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        fun getInstance(): FirebaseFunctionHTTP{
            if(retrofitClient == null){
                retrofitClient = Retrofit.Builder()
                    .baseUrl("https://us-central1-hablaveproject.cloudfunctions.net/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofitClient!!.create(FirebaseFunctionHTTP::class.java)
        }
    }
}