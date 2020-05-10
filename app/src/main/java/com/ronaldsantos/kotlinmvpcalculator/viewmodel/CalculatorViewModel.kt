package com.ronaldsantos.kotlinmvpcalculator.viewmodel

import androidx.lifecycle.ViewModel
import com.ronaldsantos.kotlinmvpcalculator.domain.domainmodel.Expression
import com.ronaldsantos.kotlinmvpcalculator.view.IViewContract
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

/**
 * This thing contains the state of the View, and makes it easy for the Presenter
 * to sort out calls to the View.
 * <p>
 *
 * Shout-out to mon ami Darel Bitsy for suggestion of making the ViewModel's data into a
 * Publisher which Presenter can subscribe to.
 * <p>
 *
 * Created by: Ronald Santos
 * <p>
 *
 * Date: 05/08/2020
 * <p>
 *
 * Time: 21:36
 */
class CalculatorViewModel(
    private val data: Expression = Expression.createSuccessModel(""),
    private val displayFlowable: BehaviorSubject<String> = BehaviorSubject.create()
) : ViewModel(), IViewContract.ViewModel {

    /**
     * This method must do two things:
     * 1. persist the UI State of the Application (obviously)
     * 2. Emit that state immediately after (as a Flowable).
     * <p>
     *
     * Step 2 is achieved by a Behaviour Subject. Once the Presenter (Subscriber), calls
     * getDisplayStatePublisher, it is given a special kind of Subject (PublisherSubject disguised
     * as a Flowable) that can emit values when I tell it to (via onNext(getDisplayState())).
     *<p>
     *
     * Step 2 in simpler language: When we give data to the ViewModel, we want the ViewModel to give
     * a copy of that new data to the Presenter instantly. This is better than having the Presenter
     * constantly ask it for the value.
     *
     */
    override fun setDisplayState(result: String) {
        this.data.result = result
        displayFlowable.onNext(getDisplayState())
    }

    override fun getDisplayStatePublisher(): Flowable<String> {
        return displayFlowable.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun getDisplayState(): String {
        return data.result
    }

}