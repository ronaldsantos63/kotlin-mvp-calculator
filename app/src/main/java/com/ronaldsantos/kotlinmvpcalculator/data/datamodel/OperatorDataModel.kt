package com.ronaldsantos.kotlinmvpcalculator.data.datamodel

import java.lang.IllegalArgumentException

/**
 * Data class for an OperatorDataModel. OperatorDataModel is one of:
 *  - char "*"; multiply
 *  - char "/"; divide
 *  - char "+"; add
 *  - char "-"; subtract
 *
 * "*" and "/" are to be evaluate before "+" and "-" as per BEDMAS
 *
 * Created by: Ronald Santos
 * Date: 05/07/2020
 * Time: 23:52
 */

data class OperatorDataModel(val operatorValue: String){
    val evaluateFirst: Boolean = checkPriority(operatorValue)

    private fun checkPriority(operatorValue: String): Boolean {
        return when(operatorValue) {
            "*" -> true
            "/" -> true
            "+" -> false
            "-" -> false
            else -> throw IllegalArgumentException("Illegal OperatorDataModel.")
        }
    }
}