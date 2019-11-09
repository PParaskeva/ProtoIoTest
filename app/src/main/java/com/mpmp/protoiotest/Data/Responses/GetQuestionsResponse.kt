package com.mpmp.protoiotest.Data.Responses


import com.google.gson.annotations.SerializedName

data class GetQuestionsResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("questions")
    val questions: List<Question?>?,
    @SerializedName("quiz_id")
    val quizId: Int?,
    @SerializedName("title")
    val title: String?
)