package com.ronaldsantos.kotlinmvpcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.ronaldsantos.kotlinmvpcalculator.dependencyinjection.Injector
import com.ronaldsantos.kotlinmvpcalculator.view.CalculatorFragment

class CalculatorActivity : AppCompatActivity() {

    companion object {
        val VIEW: String = "VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val manager: FragmentManager = this.supportFragmentManager

        // Elvis OperatorDataModel:
        // if (object/function) returns/is null :? do this
        val view = manager.findFragmentByTag(VIEW) as CalculatorFragment?
            ?: CalculatorFragment.newInstance(Injector(this))

        manager.beginTransaction().replace(R.id.root_activity_calculator, view).commit()
    }
}
