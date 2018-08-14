package com.cxt.diningplan.enums

enum class OrderStatus {

    NORMAL,
    CLOSED;

    companion object {
        fun getValue(index: Int) = when (index) {
            1, 2 -> CLOSED
            else -> NORMAL
        }
    }
}