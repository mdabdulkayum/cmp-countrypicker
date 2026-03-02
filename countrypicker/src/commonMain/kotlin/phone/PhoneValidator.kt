package phone

import core.Country
import core.internal.CountryData.countries

/**
 * Result of phone validation
 */
data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

/**
 * Phone number validator for different countries
 *
 * Validates phone numbers based on:
 * 1. Country's phoneNumberLengthRange from CountryData
 * 2. Country-specific regex patterns for format validation
 */
object PhoneValidator {

    /**
     * Validate phone number for a specific country
     *
     * @param country The country to validate against
     * @param phoneNumber The phone number (digits only)
     * @return ValidationResult with validity status and optional error message
     */
    fun validateByCountry(country: Country, phoneNumber: String): ValidationResult {
        if (phoneNumber.isEmpty()) {
            return ValidationResult(isValid = false, error = "Phone number is required")
        }

        // Use the country's phoneNumberLengthRange
        val minLength = country.phoneNumberLength.first
        val maxLength = country.phoneNumberLength.last

        // Check minimum length
        if (phoneNumber.length < minLength) {
            return ValidationResult(
                isValid = false,
                error = "Phone number too short (minimum $minLength digits)"
            )
        }

        // Check maximum length
        if (phoneNumber.length > maxLength) {
            return ValidationResult(
                isValid = false,
                error = "Phone number too long (maximum $maxLength digits)"
            )
        }

        // Validate against pattern if available
        val pattern = getPatternForCountry(country.iso2)
        if (pattern != null) {
            if (!phoneNumber.matches(pattern)) {
                return ValidationResult(
                    isValid = false,
                    error = "Invalid phone number format for ${country.name}"
                )
            }
        }

        return ValidationResult(isValid = true)
    }


    private val countryRegexMap: Map<String, Regex> by lazy {
        countries.associate { country ->
            val iso2 = country.iso2.uppercase()

            val regex = when (iso2) {

                // Special rules (keep only where truly needed)
                "US", "CA" -> Regex("^[2-9]\\d{9}$")
                "BD" -> Regex("^1[3-9]\\d{8}$")
                "IN" -> Regex("^[6-9]\\d{9}$")
                "SG" -> Regex("^[6-9]\\d{7}$")

                // Default rule → generate from lengthRange
                else -> {
                    val min = country.phoneNumberLength.first
                    val max = country.phoneNumberLength.last
                    Regex("^\\d{$min,$max}$")
                }
            }

            iso2 to regex
        }
    }

    /**
     * Get validation pattern for a country (optional advanced validation)
     *
     * Patterns are regex rules that validate specific number format requirements.
     * If no pattern exists for a country, only length validation is performed.
     *
     * @param iso2 ISO 3166-1 alpha-2 country code
     * @return Regex pattern or null if only length validation should be used
     */

    private fun getPatternForCountry(iso2: String): Regex? {
        val country = countries.find { it.iso2.equals(iso2, true) } ?: return null
        return country.customRegex
            ?: Regex("^\\d{${country.phoneNumberLength.first},${country.phoneNumberLength.last}}$")
    }

}