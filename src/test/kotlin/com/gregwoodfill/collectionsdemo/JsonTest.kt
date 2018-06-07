package com.gregwoodfill.collectionsdemo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gregwoodfill.collectionsdemo.domain.ItemResponse
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import kotlin.math.absoluteValue

class JsonTest {

    @Nested
    @DisplayName("given item response json")
    class GivenJson {
        val json = """
            {
  "payments": [
    {
      "id": 1,
      "amount": 2.5,
      "type": "PLCC"
    },
    {
      "id": 2,
      "amount": 1.55,
      "type": "VISA"
    },
    {
      "id": 3,
      "amount": 100.5,
      "type": "PLCC"
    }
  ]
}"""
        val itemResponse = jacksonObjectMapper().readValue(json, ItemResponse::class.java)

        @Test
        fun `you can add up its plcc card amount`() {
            val sumOfPlccPayments = itemResponse.payments.filter { it.type == "PLCC" }
                    .fold(BigDecimal.ZERO) { acc, payment ->
                        (acc + payment.amount).setScale(2)
                    }
            sumOfPlccPayments shouldEqual BigDecimal("103.00")
        }

        @Test
        fun floats() {
            val result = 0.1.toBigDecimal() + 0.2.toBigDecimal() - 0.3.toBigDecimal()
            println(result)
        }
        @Test
        fun `you can find group results`() {
            itemResponse.payments.sortedBy { it.type }.sortedBy { it.amount }
            val groupedByType = itemResponse.payments.groupBy { it.type }
            mapOf("a" to "b").entries.map { e -> e.key + " " + e.value }
            /*
            {
              "PLCC": [
                {
                  "id": 1,
                  "amount": 2.5,
                  "type": "PLCC"
                },
                {
                  "id": 3,
                  "amount": 100.5,
                  "type": "PLCC"
                }
              ],
              "VISA": [
                {
                  "id": 2,
                  "amount": 1.55,
                  "type": "VISA"
                }
              ]
            }

             */

            groupedByType["PLCC"]!!.size shouldEqual 2

            val response = listOf<PaymentAmount>()
            val log: Logger = LoggerFactory.getLogger(JsonTest::class.java)

            response.fold(0.0) { acc, paymentAmount ->
                log.info("The transaction amount is ${paymentAmount.transactionAmount} AND transaction code is ${paymentAmount.transactionCode}")
                acc + paymentAmount.transactionAmount }.absoluteValue
//            response.forEach { it ->
////                log.info("The transaction amount is ${it.transactionAmount} AND transaction code is ${it.transactionCode}")
//                if (it.transactionCode.equals("271")){
//                    paymentAmount += it.transactionAmount
//                } else if (it.transactionCode.equals("272")){
//                    paymentReversalAmount += it.transactionAmount
//                }
//            }
//            netPaymentAmount = paymentAmount + paymentReversalAmount
//            netPaymentAmount.absoluteValue
        }
    }

    data class PaymentAmount(val transactionAmount: Double, val transactionCode: String)
}
