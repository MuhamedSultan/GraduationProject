package com.example.we_care

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        var retrofit: Retrofit? = null

        open fun getClient(url: String): Retrofit? {

            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}