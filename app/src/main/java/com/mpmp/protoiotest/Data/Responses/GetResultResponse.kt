package com.mpmp.protoiotest.Data.Responses


import com.google.gson.annotations.SerializedName

data class GetResultResponse(
    @SerializedName("quiz_id")
    val quizId: Int?,
    @SerializedName("results")
    val results: List<Result?>?
)