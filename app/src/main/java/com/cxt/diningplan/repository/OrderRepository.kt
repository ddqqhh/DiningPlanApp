package com.cxt.diningplan.repository

import com.cxt.diningplan.api.ApiClientCreator
import com.cxt.diningplan.api.OrderApiClient

object OrderRepository {

    private val orderApiClient = ApiClientCreator.createBuilder(10).create(OrderApiClient::class.java)

    fun loadOrderList(uid: Long, key: String) = orderApiClient.loadOrder(uid, key)

    fun submitOrder(uid: Long, orderId: Long) = orderApiClient.submitOrder(uid, orderId)
}