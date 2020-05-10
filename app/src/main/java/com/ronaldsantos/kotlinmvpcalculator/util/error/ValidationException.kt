package com.ronaldsantos.kotlinmvpcalculator.util.error

import java.lang.Exception

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 14:20
 */

class ValidationException : Exception() {

    companion object {
        const val message = "Invalid Expression."
    }

}