package com.mpmp.protoiotest.Retrofit

import com.mpmp.protoiotest.Data.Responses.GetQuestionsResponse
import com.mpmp.protoiotest.Data.Responses.GetResultResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitCalls {

    @GET("quiz.json")
    suspend fun getQuestionsCall(): Response<GetQuestionsResponse>

    @GET("result.json")
    suspend fun getResultCall(): Response<GetResultResponse>
}