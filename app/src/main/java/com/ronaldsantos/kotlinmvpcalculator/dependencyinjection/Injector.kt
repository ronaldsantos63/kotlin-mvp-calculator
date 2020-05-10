package com.ronaldsantos.kotlinmvpcalculator.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ronaldsantos.kotlinmvpcalculator.data.CalculatorImpl
import com.ronaldsantos.kotlinmvpcalculator.data.ValidatorImpl
import com.ronaldsantos.kotlinmvpcalculator.domain.usecase.EvaluateExpression
import com.ronaldsantos.kotlinmvpcalculator.presenter.CalculatorPresenter
import com.ronaldsantos.kotlinmvpcalculator.util.scheduler.SchedulerProviderImpl
import com.ronaldsantos.kotlinmvpcalculator.view.CalculatorFragment
import com.ronaldsantos.kotlinmvpcalculator.view.IViewContract
import com.ronaldsantos.kotlinmvpcalculator.viewmodel.CalculatorViewModel

/**
 * Basic DI Implementation.
 * <p>
 *
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 21:26
 */
class Injector(private var activity: AppCompatActivity) {


    private var validator: ValidatorImpl = ValidatorImpl
    private var calculator: CalculatorImpl = CalculatorImpl
    private var schedulerProvider: SchedulerProviderImpl = SchedulerProviderImpl

    fun providePresenter(view: CalculatorFragment): IViewContract.Presenter {
        return CalculatorPresenter(
            view,
            ViewModelProviders.of(activity).get(CalculatorViewModel::class.java),
            schedulerProvider,
            EvaluateExpression(calculator, validator, schedulerProvider)
        )
    }
}