package com.ronaldsantos.kotlinmvpcalculator.domain.repository

import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.ExpressionDataModel
import io.reactivex.Flowable

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 01:25
 */

interface ICalculator {


    fun evaluateExpression(expression: String): Flowable<ExpressionDataModel>

}