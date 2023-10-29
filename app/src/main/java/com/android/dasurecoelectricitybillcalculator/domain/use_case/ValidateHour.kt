package com.android.dasurecoelectricitybillcalculator.domain.use_case

class ValidateHour {

    operator fun invoke(hour: String): ValidationResult {
        if (hour.isBlank()) {
            return ValidationResult(successful = false, error = "Estimated No. of Hours use is blank")
        }

        return ValidationResult(successful = true)
    }
}