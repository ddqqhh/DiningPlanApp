package com.cxt.diningplan.api

import com.cxt.diningplan.response.BaseResponse
import com.cxt.diningplan.response.OrderResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OrderApiClient {

    @FormUrlEncoded
    @POST("/api/mealOrderDetail")
    fun loadOrder(@Field("uid") uid: Long,
                  @Field("keyword") key: String): Observable<BaseResponse<List<OrderResponse>>>

    @FormUrlEncoded
    @POST("/api/mealOrderSubmit")
    fun submitOrder(@Field("uid") uid: Long,
                    @Field("id") orderId: Long): Observable<BaseResponse<Unit>>
}