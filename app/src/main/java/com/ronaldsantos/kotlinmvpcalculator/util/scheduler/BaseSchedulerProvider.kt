package com.ronaldsantos.kotlinmvpcalculator.util.scheduler

import io.reactivex.Scheduler

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 13:39
 */

interface BaseSchedulerProvider {

    fun getComputationScheduler(): Scheduler

    fun getUiScheduler(): Scheduler

    // make sure you execute Rx calls on the Schedulers.trampoline()

}