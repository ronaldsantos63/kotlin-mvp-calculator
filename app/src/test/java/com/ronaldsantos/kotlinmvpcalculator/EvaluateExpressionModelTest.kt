package com.ronaldsantos.kotlinmvpcalculator

import com.ronaldsantos.kotlinmvpcalculator.data.ValidatorImpl
import com.ronaldsantos.kotlinmvpcalculator.data.datamodel.ExpressionDataModel
import com.ronaldsantos.kotlinmvpcalculator.domain.domainmodel.Expression
import com.ronaldsantos.kotlinmvpcalculator.domain.repository.ICalculator
import com.ronaldsantos.kotlinmvpcalculator.domain.usecase.EvaluateExpression
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 02:10
 */
class EvaluateExpressionModelTest {

    @Mock
    lateinit var calc: ICalculator

    @Mock
    lateinit var validator: ValidatorImpl

    lateinit var useCase: EvaluateExpression

    val EXPRESSION = "2+2"
    val ANSWER = "4"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = EvaluateExpression(calc, validator, TestScheduler())
    }

    @Test
    fun onUseCaseExecuted() {
        val subscriber = TestSubscriber<Expression>()

        Mockito.`when`(validator.validateExpression(EXPRESSION))
            .thenReturn(
                true
            )

        Mockito.`when`(calc.evaluateExpression(EXPRESSION))
            .thenReturn(
                Flowable.just(
                    ExpressionDataModel(ANSWER, true)
                )
            )

        useCase.execute(EXPRESSION).subscribeWith(subscriber)

        Mockito.verify(validator).validateExpression(EXPRESSION)
        Mockito.verify(calc).evaluateExpression(EXPRESSION)

        assertTrue(subscriber.values()[0].result == ANSWER)
        assertTrue(subscriber.values()[0].successful)
    }

}