package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.Country
import core.CountryFilter
import core.CountryRepository
import theme.CountryPickerTheme
import theme.CountryPickerThemeDefaults
import theme.LocalCountryPickerTheme
import theme.getOnSurfaceColor
import theme.getSurfaceColor
import theme.getTypography
import ui.internal.CountryPickerList
import ui.internal.CountryPickerSearchBar


/**
 * Country picker BottomSheet - CONTROLLED API (Advanced)
 *
 * Use this when you need fine-grained control over the picker state.
 * You manage the state, the picker just renders it.
 *
 *
 * Example:
 * ```
 * val pickerState = rememberCountryPickerState(
 *     defaultCountry = CountryRepository.getByIso2("BD"),
 *     countryFilter = CountryFilter.Whitelist(setOf("US", "CA", "BD"))
 * )
 *
 * CountryPickerDialog(
 *     state = pickerState,
 *     isOpen = isPickerOpen,
 *     onDismiss = { isPickerOpen = false },
 *     onCountrySelected = { country ->
 *         println("Selected: ${country.name}")
 *         isPickerOpen = false
 *     }
 * )
 *
 * ```
 *
 * @param isOpen Whether the dialog is visible
 * @param onDismiss Called when user closes the dialog (back, outside tap, cancel button)
 * @param onCountrySelected Called when user selects a country
 * @param theme Theming configuration (colors, shapes, typography)
 * @param showDialCode Whether to display dial codes in the list
 * @param showRegion Whether to display region/continent in the list
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerBottomSheet(
    state: CountryPickerState,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onCountrySelected: (Country) -> Unit,
    theme: CountryPickerTheme = CountryPickerThemeDefaults.light(),
    showDialCode: Boolean = true,
    showRegion: Boolean = false,
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    if (isOpen) {
        CompositionLocalProvider(LocalCountryPickerTheme provides theme) {

            // Sync search filtering
            LaunchedEffect(state.searchQuery) {
                state.updateFilteredCountries()
            }

            ModalBottomSheet(
                onDismissRequest = onDismiss,
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                containerColor = theme.getSurfaceColor(),
                tonalElevation = 2.dp,
                dragHandle = { BottomSheetDefaults.DragHandle() }, // The "pill" at the top
                // windowInsets = WindowInsets.ime // Critical for keyboard support
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.ime)
                        .navigationBarsPadding() // Ensures it doesn't overlap system nav
                        .padding(bottom = 16.dp)
                ) {
                    // Header Title
                    Text(
                        text = "Select Country",
                        style = theme.getTypography().headlineSmall,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )

                    // Search Bar with improved padding
                    CountryPickerSearchBar(
                        query = state.searchQuery,
                        onQueryChange = { state.searchQuery = it },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Country list or empty state
                    Box(modifier = Modifier.weight(1f, fill = false)) {
                        if (state.filteredCountries.isNotEmpty()) {
                            CountryPickerList(
                                countries = state.filteredCountries,
                                selectedCountry = state.selectedCountry,
                                showDialCode = showDialCode,
                                showRegion = showRegion,
                                onCountryClick = { country ->
                                    state.selectCountry(country)
                                    onCountrySelected(country)
                                    if (theme.closeOnSelection) {
                                        onDismiss()
                                    }
                                }
                            )
                        } else {
                            CountryPickerEmptyState(searchQuery = state.searchQuery)
                        }
                    }
                }
            }
        }
    }
}

/**
 * State holder for controlled CountryPickerDialog
 *
 * Manages:
 * - Selected country
 * - Search query
 * - Filtered country list
 * - Country filter (all, whitelist, blacklist)
 */
class CountryPickerState(
    defaultCountry: Country? = null,
    private val countryFilter: CountryFilter = CountryFilter.All
) {
    // Selected country
    var selectedCountry: Country? by mutableStateOf(defaultCountry)
        private set

    // Search input
    var searchQuery: String by mutableStateOf("")

    // Filtered results
    var filteredCountries: List<Country> by mutableStateOf(emptyList())
        private set

    // Loading state (optional, for future async data loading)
    var isLoading: Boolean by mutableStateOf(false)
        private set

    init {
        updateFilteredCountries()
    }

    /**
     * Update filtered countries based on current search and filter
     */
    internal fun updateFilteredCountries() {
        val all = CountryRepository.filter(countryFilter)

        filteredCountries = if (searchQuery.isBlank()) {
            all
        } else {
            all.filter { country ->
                country.name.contains(searchQuery, ignoreCase = true) ||
                        country.iso2.contains(searchQuery, ignoreCase = true) ||
                        country.dialCode.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    /**
     * Select a country
     */
    fun selectCountry(country: Country) {
        selectedCountry = country
    }

    /**
     * Reset state to initial
     */
    fun reset() {
        selectedCountry = null
        searchQuery = ""
        updateFilteredCountries()
    }

    /**
     * Clear search
     */
    fun clearSearch() {
        searchQuery = ""
        updateFilteredCountries()
    }
}

/**
 * Create and remember a CountryPickerState
 *
 * Example:
 * ```
 * val state = rememberCountryPickerState(
 *     defaultCountry = CountryRepository.getByIso2("US"),
 *     countryFilter = CountryFilter.Whitelist(setOf("US", "CA", "MX"))
 * )
 * ```
 */
@Composable
fun rememberCountryPickerState(
    defaultCountry: Country? = null,
    countryFilter: CountryFilter = CountryFilter.All
): CountryPickerState = remember {
    CountryPickerState(defaultCountry, countryFilter)
}


/**
 * Empty state when no countries match search
 * Internal composable
 */
@Composable
internal fun CountryPickerEmptyState(searchQuery: String) {
    val theme = LocalCountryPickerTheme.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "No countries found with $searchQuery",
            style = theme.getTypography().bodyMedium,
            color = theme.getOnSurfaceColor().copy(alpha = theme.secondaryTextAlpha)
        )
    }
}

