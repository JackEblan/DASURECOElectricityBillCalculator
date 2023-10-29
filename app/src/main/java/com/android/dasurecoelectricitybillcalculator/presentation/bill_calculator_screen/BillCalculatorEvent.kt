package com.android.dasurecoelectricitybillcalculator.presentation.bill_calculator_screen

sealed class BillCalculatorEvent {
    object GetApplianceList : BillCalculatorEvent()

    data class OnSelectAppliance(val wattage: String, val selectedIndex: Int) : BillCalculatorEvent()

    data class OnWattageChange(val wattage: String) : BillCalculatorEvent()

    data class OnHoursChange(val hours: String) : BillCalculatorEvent()

    data class OnDaysChange(val days: String) : BillCalculatorEvent()

    data class OnRateChange(val rate: String) : BillCalculatorEvent()

    object OnCalculate : BillCalculatorEvent()
}
