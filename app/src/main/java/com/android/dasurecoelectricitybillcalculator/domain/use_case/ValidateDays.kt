package com.android.dasurecoelectricitybillcalculator.domain.use_case

class ValidateDays {

    operator fun invoke(days: String): ValidationResult {
        if (days.isBlank()) {
            return ValidationResult(successful = false, error = "Days in month is blank")
        }

        return ValidationResult(successful = true)
    }
}