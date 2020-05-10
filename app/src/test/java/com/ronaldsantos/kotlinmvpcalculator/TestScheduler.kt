package com.ronaldsantos.kotlinmvpcalculator

import com.ronaldsantos.kotlinmvpcalculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Allows testing of RxJava impl on JVM (we can't use an AndroidScheduler on the JVM).
 * <p>
 *
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 02:10
 */
class TestScheduler: BaseSchedulerProvider {
    override fun getComputationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getUiScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}