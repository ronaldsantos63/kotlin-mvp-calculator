package com.ronaldsantos.kotlinmvpcalculator

import com.ronaldsantos.kotlinmvpcalculator.viewmodel.CalculatorViewModel
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 02:10
 */
class CalculatorViewModelTest {


    val STATE = "LOLOLOLOLOLOLOLOLOLOL"

    @Test
    fun onSetDisplayState() {
        val viewModel = CalculatorViewModel()

        val subscriber = TestSubscriber<String>()

        viewModel.getDisplayStatePublisher().subscribeWith(subscriber)
        viewModel.setDisplayState(STATE)

        assertTrue(subscriber.values()[0] == STATE)
    }

}