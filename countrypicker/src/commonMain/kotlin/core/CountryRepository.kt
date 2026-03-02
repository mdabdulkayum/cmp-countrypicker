package core

import core.internal.CountryData
import core.internal.SearchEngine

/**
 * Repository for accessing country information.
 * Provides single source of truth for country data.
 */
object CountryRepository {

    /**
     * Get all countries.
     */
    fun getAll(): List<Country> = CountryData.countries

    /*init {
        DataValidator.validate(CountryData.countries)
    }*/

    /**
     * Get country by ISO-2 code (e.g., "US").
     */
    fun getByIso2(code: String): Country? =
        CountryData.getByIso2(code)

    /**
     * Get countries by dial code (e.g., "+1").
     * Some dial codes serve multiple countries.
     */
    fun getByDialCode(dialCode: String): List<Country> =
        CountryData.getByDialCode(dialCode)

    /**
     * Search countries by name, ISO code, or dial code.
     * Returns ranked results (best matches first).
     */
    fun search(query: String): List<Country> =
        SearchEngine.searchRanked(query, getAll())

    // Add to CountryRepository
    /**
     * Search within filtered countries.
     * Applies filter first, then searches.
     */
    fun search(query: String, filter: CountryFilter): List<Country> {
        val filtered = filter(filter)
        return SearchEngine.searchRanked(query, filtered)
    }

    /**
     * Get filtered countries based on filter mode.
     */
    fun filter(filter: CountryFilter): List<Country> {
        val all = getAll()
        return when (filter) {
            CountryFilter.All -> all
            is CountryFilter.Whitelist -> all.filter {
                it.iso2 in filter.countryCodes
            }
            is CountryFilter.Blacklist -> all.filter {
                it.iso2 !in filter.countryCodes
            }
        }
    }

    /**
     * Get default country (first one marked as default).
     */
    fun getDefault(): Country? =
        getAll().firstOrNull { it.isDefault }

    /**
     * Get country count (useful for debugging/testing).
     */
    fun count(): Int = getAll().size
}
