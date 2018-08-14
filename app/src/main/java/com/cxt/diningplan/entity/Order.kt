package com.cxt.diningplan.entity

import com.cxt.diningplan.enums.OrderStatus

data class Order(val id: Long,
                 val name: String,
                 val address: String,
                 val idCard: String,
                 val avatar: String,
                 val cardCode: String,
                 val status: OrderStatus)