package com.ronaldsantos.kotlinmvpcalculator.domain.usecase

import com.ronaldsantos.kotlinmvpcalculator.domain.BaseUseCase
import com.ronaldsantos.kotlinmvpcalculator.domain.domainmodel.Expression
import com.ronaldsantos.kotlinmvpcalculator.domain.repository.ICalculator
import com.ronaldsantos.kotlinmvpcalculator.domain.repository.IValidator
import com.ronaldsantos.kotlinmvpcalculator.util.error.ValidationException
import com.ronaldsantos.kotlinmvpcalculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Flowable

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 13:55
 */

class EvaluateExpression (private val calculator: ICalculator,
                          private val validator: IValidator,
                          private val scheduler: BaseSchedulerProvider): BaseUseCase<Expression> {
    override fun execute(expression: String): Flowable<Expression> {

        // Not every part of your app needs to be represented as a Data Stream.
        // In fact, if it executes fine synchronously, it's not necessary at all
        // to represent as a stream.
        if (validator.validateExpression(expression)) {
            return calculator.evaluateExpression(expression)
                .flatMap {
                    result ->
                        Flowable.just(
                            Expression.createSuccessModel(result.value)
                        )
                }
                .subscribeOn(
                    scheduler.getComputationScheduler()
                )
        }

        return Flowable.just(
            Expression.createFailureModel(ValidationException.message)
        )

    }

}