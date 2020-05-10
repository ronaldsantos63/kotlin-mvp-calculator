package com.ronaldsantos.kotlinmvpcalculator.presenter

import com.ronaldsantos.kotlinmvpcalculator.domain.domainmodel.Expression
import com.ronaldsantos.kotlinmvpcalculator.domain.usecase.EvaluateExpression
import com.ronaldsantos.kotlinmvpcalculator.util.scheduler.BaseSchedulerProvider
import com.ronaldsantos.kotlinmvpcalculator.view.IViewContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 22:40
 */
class CalculatorPresenter (private var view: IViewContract.View,
                           private var viewModel: IViewContract.ViewModel,
                           private var scheduler: BaseSchedulerProvider,
                           private val eval: EvaluateExpression): IViewContract.Presenter {

    private val eventStream = CompositeDisposable()

    private val EMPTY = ""

    override fun onEvaluateClick(expression: String) {
        // Presenter is the Observer
        eventStream.add(
            eval.execute(expression)
                .observeOn(scheduler.getUiScheduler())
                .subscribeWith(object : DisposableSubscriber<Expression>(){
                    override fun onNext(data: Expression?) {
                        if (data!!.successful){
                            viewModel.setDisplayState(data.result)
                        } else {
                            view.showError(data.result)
                        }
                    }

                    override fun onComplete() {}

                    // Reversed for fatal error
                    override fun onError(t: Throwable?) {
                        restartFeature()
                    }

                })
        )
    }

    private fun restartFeature(){
        eventStream.clear()
        view.restartFeature()
    }

    override fun onInputButtonClick(value: String) {
        viewModel.setDisplayState(
            viewModel.getDisplayState() + value
        )
    }

    override fun onDeleteClick() {
        viewModel.setDisplayState(
            viewModel.getDisplayState().dropLast(1)
        )
    }

    // Update the state, then the view.
    override fun onLongDeleteClick() {
        viewModel.setDisplayState(EMPTY)
    }

    override fun bind() {
        eventStream.add(
            // Darel's suggestions was to make publisher
            viewModel.getDisplayStatePublisher()
                .subscribeWith(
                    object : DisposableSubscriber<String>() {
                        override fun onComplete() {}

                        override fun onNext(displayState: String) {
                            view.setDisplay(displayState)
                        }

                        override fun onError(t: Throwable?) {
                            restartFeature()
                        }

                    }
                )
        )
    }

    override fun clear() {
        eventStream.clear()
    }

}