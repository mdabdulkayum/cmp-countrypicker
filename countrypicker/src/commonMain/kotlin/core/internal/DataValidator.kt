package core.internal

import core.Country

/**
 * Validates country data integrity.
 * Run at startup to catch data issues early.
 */
internal object DataValidator {

    /**
     * Validate all countries in dataset.
     * Throws exception if issues found.
     */
    fun validate(countries: List<Country>) {
        val errors = mutableListOf<String>()

        // Check for duplicates
        val iso2s = countries.map { it.iso2 }
        val duplicates = iso2s.groupingBy { it }.eachCount().filter { it.value > 1 }
        if (duplicates.isNotEmpty()) {
            errors.add("Duplicate ISO2 codes: ${duplicates.keys}")
        }

        // Check for empty strings
        countries.forEach { country ->
            if (country.name.isBlank()) errors.add("Empty country name")
            if (country.dialCode.isBlank()) errors.add("Empty dial code for ${country.iso2}")
        }

        // Check dial code format
        countries.forEach { country ->
            if (!country.dialCode.startsWith("+")) {
                errors.add("Invalid dial code format: ${country.dialCode}")
            }
        }

        if (errors.isNotEmpty()) {
            throw IllegalStateException("Country data validation failed:\n${errors.joinToString("\n")}")
        }
    }
}

// Validate at module initialization
internal val validatedCountries: List<Country> by lazy {
    val countries = CountryData.countries
    DataValidator.validate(countries)
    countries
}
