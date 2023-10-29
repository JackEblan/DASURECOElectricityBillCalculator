package com.android.dasurecoelectricitybillcalculator.domain.use_case

data class TextFieldUseCases(
    val validateWattage: ValidateWattage,
    val validateHour: ValidateHour,
    val validateDays: ValidateDays,
    val validateRate: ValidateRate
)