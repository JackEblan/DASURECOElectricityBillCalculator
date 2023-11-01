package com.android.dasurecoelectricitybillcalculator.presentation.bill_calculator_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.dasurecoelectricitybillcalculator.common.Resource
import com.android.dasurecoelectricitybillcalculator.domain.repository.JsonRepository
import com.android.dasurecoelectricitybillcalculator.domain.use_case.TextFieldUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillCalculatorViewModel @Inject constructor(
    private val repository: JsonRepository, private val textFieldUseCases: TextFieldUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(BillCalculatorState())

    val state = _state.asStateFlow()

    init {
        onEvent(BillCalculatorEvent.GetApplianceList)
    }

    fun onEvent(event: BillCalculatorEvent) {
        when (event) {
            BillCalculatorEvent.GetApplianceList -> {
                viewModelScope.launch {
                    repository.getApplianceList().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.value =
                                    _state.value.copy(isLoading = false, error = result.message)
                            }

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    isLoading = false, list = result.data ?: emptyList()
                                )
                            }

                            is Resource.Loading -> {
                                _state.value = _state.value.copy(isLoading = true)
                            }
                        }
                    }
                }
            }

            is BillCalculatorEvent.OnDaysChange -> {
                _state.value = _state.value.copy(days = event.days)
            }

            is BillCalculatorEvent.OnHoursChange -> {
                _state.value = _state.value.copy(hours = event.hours)
            }

            is BillCalculatorEvent.OnRateChange -> {
                _state.value = _state.value.copy(rate = event.rate)
            }

            is BillCalculatorEvent.OnSelectAppliance -> {
                _state.value =
                    _state.value.copy(wattage = event.wattage, selectedIndex = event.selectedIndex)
            }

            is BillCalculatorEvent.OnWattageChange -> {
                _state.value = _state.value.copy(wattage = event.wattage)
            }

            BillCalculatorEvent.OnCalculate -> {
                val wattageResult = textFieldUseCases.validateWattage(_state.value.wattage)

                val hoursResult = textFieldUseCases.validateHour(_state.value.hours)

                val daysResult = textFieldUseCases.validateDays(_state.value.days)

                val rateResult = textFieldUseCases.validateRate(_state.value.rate)

                val hasError = listOf(
                    wattageResult, hoursResult, daysResult, rateResult
                ).any { !it.successful }

                if (hasError) {
                    _state.value = _state.value.copy(
                        wattageError = wattageResult.error,
                        hoursError = hoursResult.error,
                        daysError = daysResult.error,
                        rateError = rateResult.error
                    )

                    return
                }

                val applianceMonthlyBill =
                    (((_state.value.wattage.toFloat() * _state.value.hours.toFloat()) * _state.value.days.toFloat()) / 1000) * _state.value.rate.toFloat()

                _state.value = _state.value.copy(
                    applianceMonthlyBill = "Your estimated monthly bill is ₱${
                        String.format(
                            "%.2f", applianceMonthlyBill
                        )
                    }", wattageError = null, hoursError = null, daysError = null, rateError = null
                )
            }
        }
    }
}