package com.ronaldsantos.kotlinmvpcalculator

import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.OperatorDataModel
import org.junit.Test

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 02:10
 */

class OperatorTest {

    val MULTIPLY = "*"
    val DIVIDE = "/"
    val ADD = "+"
    val SUBTRACT = "-"

    @Test
    fun TestEvaluateFirst() {
        val testOp = OperatorDataModel(MULTIPLY)

        //assert true
        assert(testOp.evaluateFirst)
    }

    @Test
    fun TestEvaluateLast() {
        val testOp = OperatorDataModel(ADD)

        //assert true
        assert(!testOp.evaluateFirst)
    }
}