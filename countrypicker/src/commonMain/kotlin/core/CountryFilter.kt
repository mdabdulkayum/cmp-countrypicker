package core

// Filter mode for countries
sealed class CountryFilter {
    data object All : CountryFilter()
    data class Whitelist(val countryCodes: Set<String>) : CountryFilter()
    data class Blacklist(val countryCodes: Set<String>) : CountryFilter()
}