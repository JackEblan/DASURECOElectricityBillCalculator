package com.android.dasurecoelectricitybillcalculator.domain.use_case

class ValidateWattage {

    operator fun invoke(wattage: String): ValidationResult {
        if (wattage.isBlank()) {
            return ValidationResult(successful = false, error = "Appliance Wattage is blank")
        }

        return ValidationResult(successful = true)
    }
}