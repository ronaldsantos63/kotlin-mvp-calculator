package com.ronaldsantos.kotlinmvpcalculator

import com.ronaldsantos.kotlinmvpcalculator.data.CalculatorImpl
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.ExpressionDataModel
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.OperandDataModel
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.OperatorDataModel
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 02:09
 */

class CalculatorImplTest {
    private val calc = CalculatorImpl

    val SIMPLE_EXPRESSION = "2+2"
    val SIMPLE_ANSWER = "4.0"

    val COMPLEX_EXPRESSION = "2+2-1*3+4"
    val COMPLEX_ANSWER = "5.0"
    val OPERANDS = listOf(
        OperandDataModel("2"),
        OperandDataModel("2"),
        OperandDataModel("1"),
        OperandDataModel("3"),
        OperandDataModel("4")
    )
    val OPERATORS = listOf(
        OperatorDataModel("+"),
        OperatorDataModel("-"),
        OperatorDataModel("*"),
        OperatorDataModel("+")
    )
    val INVALID_EXPRESSION_ONE = "2+"
    val INVALID_EXPRESSION_TWO = "+2"
    val INVALID_EXPRESSION_THREE = "2+-"
    val INVALID_ANSWER = "Error: Invalid ExpressionDataModel"

    /**
     * Get operands of current expression
     */
    @Test
    fun getOperands() {
        val operands: List<OperandDataModel> = calc.getOperands(COMPLEX_EXPRESSION)

        assertTrue(operands == OPERANDS)
    }

    @Test
    fun getOperators() {
        val operatorDataModels: List<OperatorDataModel> = calc.getOperators(COMPLEX_EXPRESSION)

        assertTrue(operatorDataModels == OPERATORS)
    }

    @Test
    fun onEvaluateValidSimpleExpression() {
        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(SIMPLE_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == SIMPLE_ANSWER)
    }
    //
    @Test
    fun onEvaluateValidComplexExpression() {
        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(COMPLEX_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == COMPLEX_ANSWER)
    }

}