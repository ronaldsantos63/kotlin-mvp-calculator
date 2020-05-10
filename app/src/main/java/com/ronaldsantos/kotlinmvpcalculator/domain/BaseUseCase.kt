package com.ronaldsantos.kotlinmvpcalculator.domain

import io.reactivex.Flowable

/**
 * [T] stands for a generic "Type"
 *
 * We know we want [T] to be something, but let's decide what [T]
 * is, when we create the class which will inherit from [BaseUseCase]
 *
 * <p></p><p>
 *
 * Created by: Ronald Santos
 *
 * <p>
 *
 * Date: 05/08/2020
 * <p>
 *
 * Time: 13:34
 */

interface BaseUseCase<T> {
    fun execute(expression: String): Flowable<T>
}