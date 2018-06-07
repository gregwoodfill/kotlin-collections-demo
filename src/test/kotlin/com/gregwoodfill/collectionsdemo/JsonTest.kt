package com.gregwoodfill.collectionsdemo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gregwoodfill.collectionsdemo.domain.ItemResponse
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

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
        fun `you can find group results`() {
            val groupedByType = itemResponse.payments.groupBy { it.type }
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
        }
    }
}