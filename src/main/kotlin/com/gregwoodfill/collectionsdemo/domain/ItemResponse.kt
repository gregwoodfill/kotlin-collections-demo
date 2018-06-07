package com.gregwoodfill.collectionsdemo.domain

import java.math.BigDecimal

data class ItemResponse(val payments: List<Payment>)
data class Payment(val id: Int, val amount: BigDecimal, val type: String)