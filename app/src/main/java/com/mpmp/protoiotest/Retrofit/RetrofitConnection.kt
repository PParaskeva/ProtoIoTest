package com.mpmp.protoiotest.Retrofit

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class RetrofitConnection {

    fun getConnection(): RetrofitCalls {
        val BASE_URL = "https://proto.io/en/jobs/candidate-questions/"
        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(
                CertificatePinner.Builder()
                    .add(
                        "*.proto.io",
                        "sha256/gxaQQ0dHbHxJIc7kt2BOGLoLR9z3ptIDjMH6idtHxxs="
                    )
                    .build()
            )
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(RetrofitCalls::class.java)
    }
}