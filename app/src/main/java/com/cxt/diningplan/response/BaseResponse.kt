package com.cxt.diningplan.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(@SerializedName("statusCode") val statusCode: Int,
                           @SerializedName("message") val message: String,
                           @SerializedName("result") val data: T?)