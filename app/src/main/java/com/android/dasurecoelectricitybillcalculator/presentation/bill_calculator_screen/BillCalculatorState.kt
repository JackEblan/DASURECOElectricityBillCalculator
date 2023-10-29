package com.android.dasurecoelectricitybillcalculator.presentation.bill_calculator_screen

import com.android.dasurecoelectricitybillcalculator.domain.model.ApplianceItem

data class BillCalculatorState(
    val isLoading: Boolean = true,
    val list: List<ApplianceItem> = emptyList(),
    val error: String? = null,
    val wattage: String = "",
    val wattageError: String? = null,
    val hours: String = "",
    val hoursError: String? = null,
    val days: String = "",
    val daysError: String? = null,
    val rate: String = "",
    val rateError: String? = null,
    val applianceMonthlyBill: String? = null,
    val selectedIndex: Int? = null
)