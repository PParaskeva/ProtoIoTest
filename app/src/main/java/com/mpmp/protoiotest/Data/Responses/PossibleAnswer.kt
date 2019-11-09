package com.mpmp.protoiotest.Data.Responses


import com.google.gson.annotations.SerializedName

data class PossibleAnswer(
    @SerializedName("a_id")
    val aId: Int?,
    @SerializedName("caption")
    val caption: String?
)