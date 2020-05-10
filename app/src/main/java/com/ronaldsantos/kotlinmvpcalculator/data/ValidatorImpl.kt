package com.ronaldsantos.kotlinmvpcalculator.data

import com.ronaldsantos.kotlinmvpcalculator.domain.repository.IValidator

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 01:36
 */

//Note.: The object prevents the class from being instantiated more than once, acting as a singleton

object ValidatorImpl : IValidator {
    override fun validateExpression(expression: String): Boolean {
        // Check for valid starting/ending chars
        if (invalidStart(expression)) return false
        if (invalidEnd(expression)) return false

        // Check for concurrent decimals and operators like "2++2"
        if (hasConcurrentOperators(expression)) return false
        if (hasConcurrentDecimals(expression)) return false

        return true
    }

    private fun invalidStart(expression: String): Boolean {
        return when {
            expression.startsWith("+") -> true
            expression.startsWith("-") -> true
            expression.startsWith("*") -> true
            expression.startsWith("/") -> true
            expression.startsWith(".") -> true
            else -> false
        }
    }

    private fun invalidEnd(expression: String): Boolean {
        return when {
            expression.endsWith("+") -> true
            expression.endsWith("-") -> true
            expression.endsWith("*") -> true
            expression.endsWith("/") -> true
            expression.endsWith(".") -> true
            else -> false
        }
    }


    private fun isOperator(char: Char): Boolean {
        return when {
            //not sure why I had to toString() but char.equals("+") was not working as expected
            char.toString() == "+" -> true
            char.toString() == "-" -> true
            char.toString() == "*" -> true
            char.toString() == "/" -> true
            else -> false
        }
    }


    private fun isConcurrentOperator(current: Char, next: Char): Boolean {
        if (isOperator(current) && isOperator(next)){
            return true
        }
        return false
    }

    private fun hasConcurrentOperators(expression: String): Boolean {
        expression.indices
            .forEach {
                if (it < expression.lastIndex) {
                    if (isConcurrentOperator(expression[it], expression[it + 1])){
                        return true
                    }
                }
            }
        return false
    }

    private fun isConcurrentDecimal(current: Char, next: Char): Boolean {
        if (current.toString() == "." && next.toString() == "."){
            return true
        }
        return false
    }

    private fun hasConcurrentDecimals(expression: String): Boolean {
        expression.indices
            .forEach {
                if (it < expression.lastIndex) {
                    if (isConcurrentDecimal(expression[it], expression[it + 1])){
                        return true
                    }
                }
            }
        return false
    }

}