package com.cxt.diningplan.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(@SerializedName("id") val id: Long,
                         @SerializedName("mid") val mid: Long,
                         @SerializedName("mealTime") val planTime: String,
                         @SerializedName("type") val orderType: Int,
                         @SerializedName("status") val orderStatus: Int,
                         @SerializedName("cardNo") val cardCode: String,
                         @SerializedName("name") val name: String,
                         @SerializedName("address") val address: String,
                         @SerializedName("avatar") val avatar: String,
                         @SerializedName("idCardNumber") val idCard: String)