package com.ronaldsantos.kotlinmvpcalculator.view

import io.reactivex.Flowable

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 21:20
 */

interface IViewContract  {

    interface View {
        fun getCurrentExpression(): String
        fun setDisplay(value: String)
        fun showError(value: String)
        fun restartFeature()
    }

    interface ViewModel {
        fun setDisplayState(result: String)


        // Get something (Flowable in this case)
        // which will wmit a CalcUIModel as soon as it is set (above method)
        fun getDisplayStatePublisher(): Flowable<String>

        fun getDisplayState(): String
    }

    interface Presenter {
        fun onEvaluateClick(expression: String)
        fun onInputButtonClick(value: String)
        fun onDeleteClick()
        fun onLongDeleteClick()
        fun bind()
        fun clear()
    }

}