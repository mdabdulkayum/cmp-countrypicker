package phone

/**
 * Phone number formatter for different countries
 *
 * Formats phone numbers into country-specific display formats based on ISO 3166-1 alpha-2 codes.
 * Works seamlessly with Country data objects.
 *
 * Supports 16+ countries with customized formatting patterns.
 */
object PhoneFormatter {

    /**
     * Format phone number based on country code
     *
     * @param countryCode ISO 3166-1 alpha-2 country code (e.g., "US", "GB", "BD")
     * @param nationalNumber The phone number (digits only, without country code)
     * @param shouldFormat Whether to apply formatting or return digits only
     * @return Formatted phone number string
     *
     * Examples:
     * ```
     * PhoneFormatter.format("US", "5551234567", true)   // Returns "(555) 123-4567"
     * PhoneFormatter.format("BD", "1750123456", true)   // Returns "1750-123-456"
     * PhoneFormatter.format("GB", "2071946000", true)   // Returns "2071 946 0000"
     * PhoneFormatter.format("IN", "9876543210", true)   // Returns "98765 43210"
     * ```
     */
    fun format(
        countryCode: String,
        nationalNumber: String,
        shouldFormat: Boolean = true
    ): String {
        if (!shouldFormat || nationalNumber.isEmpty()) {
            return nationalNumber
        }

        return when (countryCode.uppercase()) {
            // North America
            "US" -> formatUS(nationalNumber)
            "CA" -> formatUS(nationalNumber)

            // Europe
            "GB" -> formatGB(nationalNumber)
            "DE" -> formatDE(nationalNumber)
            "FR" -> formatFR(nationalNumber)
            "ES" -> formatES(nationalNumber)
            "IT" -> formatIT(nationalNumber)

            // Asia
            "IN" -> formatIN(nationalNumber)
            "BD" -> formatBD(nationalNumber)
            "JP" -> formatJP(nationalNumber)
            "SG" -> formatSG(nationalNumber)

            // Oceania
            "AU" -> formatAU(nationalNumber)
            "NZ" -> formatNZ(nationalNumber)

            // Americas
            "BR" -> formatBR(nationalNumber)
            "MX" -> formatMX(nationalNumber)

            // Africa
            "ZA" -> formatZA(nationalNumber)

            // Middle East
            "BH" -> formatBH(nationalNumber)
            "AE" -> formatAE(nationalNumber)

            // Default: return unformatted for unsupported countries
            else -> nationalNumber
        }
    }

    /**
     * Format: (XXX) XXX-XXXX
     * Used for: US, Canada
     * Example: 5551234567 → (555) 123-4567
     */
    private fun formatUS(number: String): String {
        if (number.length != 10) return number
        return "(${number.substring(0, 3)}) ${number.substring(3, 6)}-${number.substring(6)}"
    }

    /**
     * Format: XXXX XXXX XXXX (10 digits) or XXXX XXXXX XXXX (11 digits)
     * Used for: United Kingdom
     * Example: 2071946000 → 2071 946 0000
     */
    private fun formatGB(number: String): String {
        return when {
            number.length == 10 -> "${number.substring(0, 4)} ${number.substring(4, 7)} ${number.substring(7)}"
            number.length == 11 -> "${number.substring(0, 4)} ${number.substring(4, 7)} ${number.substring(7)}"
            else -> number
        }
    }

    /**
     * Format: XXXX XXXXXXX
     * Used for: Germany
     * Example: 30123456789 → 3012 3456789
     */
    private fun formatDE(number: String): String {
        return when {
            number.length in 10..11 -> "${number.substring(0, 4)} ${number.substring(4)}"
            number.length in 12..13 -> "${number.substring(0, 4)} ${number.substring(4, 8)} ${number.substring(8)}"
            else -> number
        }
    }

    /**
     * Format: X XX XX XX XX
     * Used for: France
     * Example: 123456789 → 1 23 45 67 89
     */
    private fun formatFR(number: String): String {
        if (number.length != 9) return number
        return "${number.substring(0, 1)} ${number.substring(1, 3)} ${number.substring(3, 5)} ${number.substring(5, 7)} ${number.substring(7)}"
    }

    /**
     * Format: XXX XX XX XX
     * Used for: Spain
     * Example: 912345678 → 912 34 56 78
     */
    private fun formatES(number: String): String {
        if (number.length != 9) return number
        return "${number.substring(0, 3)} ${number.substring(3, 5)} ${number.substring(5, 7)} ${number.substring(7)}"
    }

    /**
     * Format: XXX XXXX XXX
     * Used for: Italy
     * Example: 3312345678 → 331 1234 567
     */
    private fun formatIT(number: String): String {
        if (number.length != 10) return number
        return "${number.substring(0, 3)} ${number.substring(3, 7)} ${number.substring(7)}"
    }

    /**
     * Format: XXXXX XXXXX
     * Used for: India
     * Example: 9876543210 → 98765 43210
     */
    private fun formatIN(number: String): String {
        if (number.length != 10) return number
        return "${number.substring(0, 5)} ${number.substring(5)}"
    }

    /**
     * Format: XXXX-XXX-XXXX
     * Used for: Bangladesh
     * Example: 1750123456 → 1750-123-456
     */
    private fun formatBD(number: String): String {
        if (number.length != 11) return number
        return "${number.substring(0, 4)}-${number.substring(4, 7)}-${number.substring(7)}"
    }

    /**
     * Format: XX-XXXX-XXXX (10 digits) or XX-XXXX-XXXX (11 digits)
     * Used for: Japan
     * Example: 9012345678 → 90-1234-5678
     */
    private fun formatJP(number: String): String {
        return when {
            number.length == 10 -> "${number.substring(0, 2)}-${number.substring(2, 6)}-${number.substring(6)}"
            number.length == 11 -> "${number.substring(0, 3)}-${number.substring(3, 7)}-${number.substring(7)}"
            else -> number
        }
    }

    /**
     * Format: XXXX XXXX
     * Used for: Singapore
     * Example: 65123456 → 6512 3456
     */
    private fun formatSG(number: String): String {
        if (number.length != 8) return number
        return "${number.substring(0, 4)} ${number.substring(4)}"
    }

    /**
     * Format: XXXX XXX XXX
     * Used for: Australia
     * Example: 234567890 → 2345 678 90
     */
    private fun formatAU(number: String): String {
        if (number.length != 9) return number
        return "${number.substring(0, 4)} ${number.substring(4, 7)} ${number.substring(7)}"
    }

    /**
     * Format: XXX XXXX XXX
     * Used for: New Zealand
     * Example: 201234567 → 201 1234 567
     */
    private fun formatNZ(number: String): String {
        if (number.length != 9) return number
        return "${number.substring(0, 3)} ${number.substring(3, 7)} ${number.substring(7)}"
    }

    /**
     * Format: (XX) XXXXX-XXXX
     * Used for: Brazil
     * Example: 1112345678 → (11) 12345-6789
     */
    private fun formatBR(number: String): String {
        if (number.length != 11) return number
        return "(${number.substring(0, 2)}) ${number.substring(2, 7)}-${number.substring(7)}"
    }

    /**
     * Format: XX XXXX XXXX
     * Used for: Mexico
     * Example: 5512345678 → 55 1234 5678
     */
    private fun formatMX(number: String): String {
        if (number.length != 10) return number
        return "${number.substring(0, 2)} ${number.substring(2, 6)} ${number.substring(6)}"
    }

    /**
     * Format: XX XXXX XXXX
     * Used for: South Africa
     * Example: 1012345678 → 10 1234 5678
     */
    private fun formatZA(number: String): String {
        if (number.length != 10) return number
        return "${number.substring(0, 2)} ${number.substring(2, 6)} ${number.substring(6)}"
    }

    /**
     * Format: XXXX XXXX
     * Used for: Bahrain
     * Example: 33123456 → 3312 3456
     */
    private fun formatBH(number: String): String {
        if (number.length != 8) return number
        return "${number.substring(0, 4)} ${number.substring(4)}"
    }

    /**
     * Format: XX XXXX XXXX
     * Used for: United Arab Emirates
     * Example: 501234567 → 50 1234 567
     */
    private fun formatAE(number: String): String {
        if (number.length != 9) return number
        return "${number.substring(0, 2)} ${number.substring(2, 6)} ${number.substring(6)}"
    }
}