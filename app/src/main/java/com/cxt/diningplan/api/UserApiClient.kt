package com.cxt.diningplan.api

import com.cxt.diningplan.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApiClient {

    @FormUrlEncoded
    @POST("/api/login")
    fun login(@Field("username") account: String,
              @Field("encrypt") password: String): Observable<LoginResponse>
}