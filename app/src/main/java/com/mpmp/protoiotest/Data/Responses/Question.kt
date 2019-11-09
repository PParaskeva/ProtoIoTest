package com.mpmp.protoiotest.Data.Responses


import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("correct_answer")
    val correctAnswer: Any?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("points")
    val points: Int?,
    @SerializedName("possible_answers")
    val possibleAnswers: List<PossibleAnswer?>?,
    @SerializedName("q_id")
    val qId: Int?,
    @SerializedName("question_type")
    val questionType: String?,
    @SerializedName("title")
    val title: String?
)