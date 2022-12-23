package com.example.giphy.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class GiphyApi {

    companion object {
        const val BASE_URL = "https://api.giphy.com/v1/"

        //API KEY SHOULD NOT BE IN THE CODE
        const val API_KEY = "tgRrHDbWNcsMwgsfP8Kq0bv4bq2hAHHw"
        const val API_QUERY_KEY = "api_key"
    }

    private val client = OkHttpClient.Builder()
        .apply {
            addInterceptor { chain ->
                val request = chain.request()

                chain.proceed(
                    request
                        .newBuilder()
                        .url(
                            request.url
                                .newBuilder()
                                .addQueryParameter(API_QUERY_KEY, API_KEY)
                                .build()
                        )
                        .build()
                ).also {
                    Timber.d("call: $it")
                }
            }
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T = retrofit.create(service)
}