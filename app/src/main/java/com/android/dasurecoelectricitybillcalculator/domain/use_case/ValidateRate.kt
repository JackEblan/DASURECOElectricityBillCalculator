package com.android.dasurecoelectricitybillcalculator.domain.use_case

class ValidateRate {

    operator fun invoke(rate: String): ValidationResult {
        if (rate.isBlank()) {
            return ValidationResult(successful = false, error = "Power rate is blank")
        }

        return ValidationResult(successful = true)
    }
}