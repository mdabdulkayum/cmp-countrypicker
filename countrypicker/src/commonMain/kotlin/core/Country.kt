package core

/**
 * Represents a country with telephony information.
 *
 * @param name The English name of the country (e.g., "United States")
 * @param iso2 ISO 3166-1 alpha-2 code (e.g., "US")
 * @param iso3 ISO 3166-1 alpha-3 code (e.g., "USA")
 * @param dialCode International dialing code (e.g., "+1")
 * @param flagEmoji Unicode flag emoji (e.g., "🇺🇸")
 * @param region Geographic region for optional grouping (e.g., "North America")
 * @param phoneNumberLength Expected phone number length (without country code)
 * @param customRegex
 * @param isDefault Whether this is the default/pre-selected country
 */
data class Country(
    val name: String,
    val iso2: String,
    val iso3: String,
    val dialCode: String,
    val flagEmoji: String,
    val region: String = "",
    val phoneNumberLength: IntRange = 7..15,  // Typical range for most countries
    val customRegex: Regex? = null,
    val isDefault: Boolean = false
) {
    init {
        require(iso2.length == 2 && iso2.all { it.isUpperCase() }) {
            "ISO2 code must be exactly 2 uppercase letters, got: $iso2"
        }
        require(iso3.length == 3 && iso3.all { it.isUpperCase() }) {
            "ISO3 code must be exactly 3 uppercase letters, got: $iso3"
        }
        require(dialCode.startsWith("+")) {
            "Dial code must start with '+', got: $dialCode"
        }
        require(flagEmoji.isNotEmpty()) {
            "Flag emoji cannot be empty"
        }
    }

    /**
     * Get searchable text combining name, ISO codes, and dial code.
     * Used for search indexing and filtering.
     */
    fun getSearchableText(): String =
        "$name $iso2 $iso3 $dialCode".lowercase()

    override fun toString(): String =
        "$flagEmoji $name ($iso2)"

    /**
     * Check if this country matches a given ISO code (any format).
     */
    fun matchesCode(code: String): Boolean {
        val normalized = code.uppercase()
        return iso2 == normalized || iso3 == normalized
    }
}