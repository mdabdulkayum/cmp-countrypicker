package core.internal

import core.Country

/**
 * Efficient search across country name, ISO codes, and dial code.
 * Supports partial matching and prefix matching.
 */

internal object SearchEngine {

    /**
     * Search countries by query.
     * Matches against:
     * - Country name (partial, case-insensitive)
     * - ISO-2 code (prefix)
     * - ISO-3 code (prefix)
     * - Dial code (prefix)
     */


    /**
     * Pre-computed searchable text per country (lazy)
     */
    private val searchIndexCache: MutableMap<String, String> = mutableMapOf()

    private fun getSearchableText(country: Country): String {
        return searchIndexCache.getOrPut(country.iso2) {
            country.getSearchableText()
        }
    }


    fun search(
        query: String,
        countries: List<Country>
    ): List<Country> {
        if (query.isBlank()) return countries

        val normalized = query.lowercase().trim()

        return countries.filter { country ->
            when {
                // Dial code match (e.g., user types "+1" or "1")
                country.dialCode.startsWith(normalized) ||
                        country.dialCode.startsWith("+$normalized") -> true

                // ISO-2 code match (e.g., "US", "us")
                country.iso2.lowercase().startsWith(normalized) -> true

                // ISO-3 code match (e.g., "USA")
                country.iso3.lowercase().startsWith(normalized) -> true

                // Country name match (e.g., "united", "states")
                country.name.lowercase().contains(normalized) -> true

                // Diacritic-insensitive match (e.g., "Cote" for "Côte d'Ivoire")
                removeDiacritics(country.name).lowercase().contains(normalized) -> true

                else -> false
            }
        }
    }

    /**
     * Rank results by relevance.
     * Exact matches first, then prefix, then substring.
     */
    fun searchRanked(
        query: String,
        countries: List<Country>
    ): List<Country> {
        if (query.isBlank()) return countries

        val normalized = query.lowercase().trim()

        return search(query, countries).sortedWith(
            compareBy<Country> { country ->
                when {
                    // Exact match on ISO2
                    country.iso2.equals(normalized, ignoreCase = true) -> 0
                    // Dial code exact match
                    country.dialCode == normalized ||
                            country.dialCode == "+$normalized" -> 1
                    // Name starts with query
                    country.name.lowercase().startsWith(normalized) -> 2
                    // ISO code prefix
                    country.iso2.lowercase().startsWith(normalized) ||
                            country.iso3.lowercase().startsWith(normalized) -> 3
                    // Substring match
                    else -> 4
                }
            }
        )
    }

    /**
     * Remove diacritics for fuzzy matching.
     * "Côte" -> "Cote", "São Paulo" -> "Sao Paulo"
     *
     * Multiplatform-compatible using a simple character mapping approach.
     * Covers common European diacritics used in country names.
     */

    // Create once, reuse forever
    private val DIACRITIC_MAP = mapOf(
        // Lowercase
        'à' to 'a', 'á' to 'a', 'â' to 'a', 'ã' to 'a', 'ä' to 'a', 'å' to 'a', 'ă' to 'a',
        'ç' to 'c', 'č' to 'c',
        'đ' to 'd', 'ď' to 'd', 'ð' to 'd',
        'è' to 'e', 'é' to 'e', 'ê' to 'e', 'ë' to 'e', 'ě' to 'e',
        'ğ' to 'g', 'ģ' to 'g',
        'ì' to 'i', 'í' to 'i', 'î' to 'i', 'ï' to 'i', 'ı' to 'i',
        'ķ' to 'k',
        'ł' to 'l', 'ľ' to 'l', 'ļ' to 'l',
        'ñ' to 'n', 'ń' to 'n', 'ň' to 'n', 'ņ' to 'n',
        'ò' to 'o', 'ó' to 'o', 'ô' to 'o', 'õ' to 'o', 'ö' to 'o', 'ø' to 'o', 'ő' to 'o',
        'ř' to 'r', 'ŗ' to 'r',
        'ś' to 's', 'ş' to 's', 'š' to 's', 'ș' to 's',
        'ţ' to 't', 'ť' to 't', 'ț' to 't',
        'ù' to 'u', 'ú' to 'u', 'û' to 'u', 'ü' to 'u', 'ű' to 'u', 'ų' to 'u',
        'ý' to 'y', 'ỳ' to 'y', 'ỹ' to 'y',
        'ź' to 'z', 'ż' to 'z', 'ž' to 'z',
        'ß' to 's',
        'æ' to 'a', 'œ' to 'o',

        // Uppercase
        'À' to 'A', 'Á' to 'A', 'Â' to 'A', 'Ã' to 'A', 'Ä' to 'A', 'Å' to 'A', 'Ă' to 'A',
        'Ç' to 'C', 'Č' to 'C',
        'Đ' to 'D', 'Ď' to 'D', 'Ð' to 'D',
        'È' to 'E', 'É' to 'E', 'Ê' to 'E', 'Ë' to 'E', 'Ě' to 'E',
        'Ğ' to 'G', 'Ģ' to 'G',
        'Ì' to 'I', 'Í' to 'I', 'Î' to 'I', 'Ï' to 'I', 'İ' to 'I',
        'Ķ' to 'K',
        'Ł' to 'L', 'Ľ' to 'L', 'Ļ' to 'L',
        'Ñ' to 'N', 'Ń' to 'N', 'Ň' to 'N', 'Ņ' to 'N',
        'Ò' to 'O', 'Ó' to 'O', 'Ô' to 'O', 'Õ' to 'O', 'Ö' to 'O', 'Ø' to 'O', 'Ő' to 'O',
        'Ř' to 'R', 'Ŗ' to 'R',
        'Ś' to 'S', 'Ş' to 'S', 'Š' to 'S', 'Ș' to 'S',
        'Ţ' to 'T', 'Ť' to 'T', 'Ț' to 'T',
        'Ù' to 'U', 'Ú' to 'U', 'Û' to 'U', 'Ü' to 'U', 'Ű' to 'U', 'Ų' to 'U',
        'Ý' to 'Y', 'Ỳ' to 'Y', 'Ỹ' to 'Y',
        'Ź' to 'Z', 'Ż' to 'Z', 'Ž' to 'Z',
        'Æ' to 'A', 'Œ' to 'O'
    )

    private fun removeDiacritics(input: String): String {
        return input.map { char ->
            DIACRITIC_MAP[char] ?: char
        }.joinToString("")
    }
}