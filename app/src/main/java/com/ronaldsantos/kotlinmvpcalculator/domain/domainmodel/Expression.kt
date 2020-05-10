package com.ronaldsantos.kotlinmvpcalculator.domain.domainmodel

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 13:22
 */
class Expression private constructor(
    var result: String,
    var successful: Boolean) {

    // You can only have 1 companion object per class
    companion object Factory{
        fun createSuccessModel(result: String): Expression {
            return Expression(result, true)
        }

        fun createFailureModel(result: String): Expression {
            return Expression(result, false)
        }
        // could even do createLoadingModel if appropriate. This App is simple enough that it doesn't need long running operations
    }
}