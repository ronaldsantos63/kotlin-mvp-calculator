package com.ronaldsantos.kotlinmvpcalculator.data

import androidx.annotation.VisibleForTesting
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.ExpressionDataModel
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.OperandDataModel
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.OperatorDataModel
import com.ronaldsantos.kotlinmvpcalculator.domain.repository.ICalculator
import io.reactivex.Flowable
import java.lang.IllegalArgumentException

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 02:16
 */

object CalculatorImpl : ICalculator {

    @VisibleForTesting
    internal fun getOperators(expression: String): MutableList<OperatorDataModel> {
        //this ugly stuff is called Regex; Regular ExpressionDataModel/
        //Basically saying split based on number or decimal numbers.
        val operators = expression.split(Regex("\\d+(?:\\.\\d+)?"))
            .filterNot { it == "" }
            .toMutableList()
        val output: MutableList<OperatorDataModel> = arrayListOf()

        operators.indices.mapTo(output) {
            OperatorDataModel(operators[it])
        }
        return output
    }

    @VisibleForTesting
    internal fun getOperands(expression: String): MutableList<OperandDataModel> {
        val operands = expression.split("+", "-", "/", "*")
        val output: MutableList<OperandDataModel> = arrayListOf()

        // Kotlin's answer to enhanced for loop
        operands.indices.mapTo(output) {
            OperandDataModel(operands[it])
        }
        return output
    }

    @VisibleForTesting
    internal fun evaluatePair(firstOperand: OperandDataModel, secondOperand: OperandDataModel, operatorDataModel: OperatorDataModel): String {
        when (operatorDataModel.operatorValue) {
            "+" -> return (firstOperand.value.toFloat() + secondOperand.value.toFloat()).toString()
            "-" -> return (firstOperand.value.toFloat() - secondOperand.value.toFloat()).toString()
            "/" -> return (firstOperand.value.toFloat() / secondOperand.value.toFloat()).toString()
            "*" -> return (firstOperand.value.toFloat() * secondOperand.value.toFloat()).toString()
        }
        throw IllegalArgumentException("Illegal Operator.")
    }

    private fun evaluate(expression: String): Flowable<ExpressionDataModel> {
        // get ops and ops
        val operatorDataModels: MutableList<OperatorDataModel> = getOperators(expression)
        val operands: MutableList<OperandDataModel> = getOperands(expression)

        while (operands.size > 1) {
            val firstOperand = operands[0]
            val secondOperand = operands[1]
            val firstOperator = operatorDataModels[0]

            //if op is * or / (evaluateFirst), or no more operatorDataModels to follow,
            // or next op is NOT (evaluateFirst)
            if (firstOperator.evaluateFirst ||
                    operatorDataModels.elementAtOrNull(1) == null ||
                    !operatorDataModels[1].evaluateFirst) {
                val result = OperandDataModel(evaluatePair(firstOperand, secondOperand, firstOperator))
                operatorDataModels.remove(firstOperator)
                operands.remove(firstOperand)
                operands.remove(secondOperand)

                operands.add(0, result)
            } else {

                val thirdOperand = operands[2]
                val secondOperator = operatorDataModels[1]
                val result = OperandDataModel(evaluatePair(secondOperand, thirdOperand, secondOperator))

                operatorDataModels.remove(secondOperator)
                operands.remove(secondOperand)
                operands.remove(thirdOperand)

                operands.add(1, result)
            }
        }

        // when calculations are finished, emit the result
        return Flowable.just(ExpressionDataModel(operands[0].value, true))
    }

    override fun evaluateExpression(expression: String): Flowable<ExpressionDataModel> {
        return evaluate(expression)
    }

}