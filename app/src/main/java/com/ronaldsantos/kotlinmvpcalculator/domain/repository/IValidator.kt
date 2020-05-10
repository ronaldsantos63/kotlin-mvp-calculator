package com.ronaldsantos.kotlinmvpcalculator.domain.repository

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 01:25
 */

interface IValidator {


    fun validateExpression(expression: String): Boolean

}