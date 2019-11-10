package com.mpmp.protoiotest.Data.Responses


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("img")
    val img: String?,
    @SerializedName("maxpoints")
    val maxpoints: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("minpoints")
    val minpoints: Int?,
    @SerializedName("r_id")
    val rId: Int?,
    @SerializedName("title")
    val title: String?
)