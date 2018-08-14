package com.cxt.diningplan.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClientCreator {

    private const val BASE_URL = "http://api.healthtop.cn"
//    private const val BASE_URL = "http://192.168.0.114"

    private fun createClient(waitTime: Int) = OkHttpClient.Builder()
            .connectTimeout(waitTime.toLong(), TimeUnit.SECONDS)
            .readTimeout(waitTime.toLong(), TimeUnit.SECONDS)
            .writeTimeout(waitTime.toLong(), TimeUnit.SECONDS)
            .build()

    fun createBuilder(waitTime: Int): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient(waitTime))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}