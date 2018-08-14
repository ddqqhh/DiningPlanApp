package com.cxt.diningplan.mapper

import com.cxt.diningplan.common.CommonConst
import com.cxt.diningplan.entity.Order
import com.cxt.diningplan.enums.OrderStatus
import com.cxt.diningplan.response.OrderResponse

object OrderMapper {

    fun getOrderList(response: List<OrderResponse>?): List<Order> {
        val result = arrayListOf<Order>()
        response?.forEach {
            Order(id = it.id,
                    name = it.name,
                    address = it.address,
                    idCard = it.idCard,
                    cardCode = it.cardCode,
                    avatar = CommonConst.BASE_QINIU_URL + it.avatar,
                    status = OrderStatus.getValue(it.orderStatus))
                    .let { order -> result.add(order) }
        }
        return result
    }
}