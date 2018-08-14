package com.cxt.diningplan.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("statusCode") val statusCode: Int,
                         @SerializedName("message") val message: String,
                         @SerializedName("result") val result: Result?) {
    data class Result(@SerializedName("uid") val uid: Long)
}