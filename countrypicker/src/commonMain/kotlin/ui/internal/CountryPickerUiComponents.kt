package ui.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import core.Country
import theme.CountryPickerTheme
import theme.CountryPickerThemeDefaults
import theme.LocalCountryPickerTheme
import theme.getContainerColor
import theme.getOnContainerColor
import theme.getOnSurfaceColor
import theme.getOutlineColor
import theme.getPrimaryColor
import theme.getSurfaceColor
import theme.getTypography



@Composable
internal fun CountryPickerSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    theme: CountryPickerTheme = CountryPickerThemeDefaults.light()
) {
  //  val theme = LocalCountryPickerTheme.current

    val customTextSelectionColors = TextSelectionColors(
        handleColor = theme.getPrimaryColor(),
        backgroundColor = theme.getPrimaryColor().copy(alpha = 0.2f)
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text("Search country or dial code...") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },

            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            shape = CircleShape, // Modern rounded look
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = theme.getOutlineColor(),
                focusedContainerColor = theme.getOutlineColor().copy(alpha = 0.1f),
                unfocusedContainerColor = theme.getOutlineColor().copy(alpha = 0.05f)
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )
    }
}


/**
 * Enhanced Country List with lazy loading and smooth scrolling
 *
 * Features:
 * - Virtualization (lazy loading)
 * - Smooth scroll animation
 * - Scroll indicator
 * - Item animations
 */
@Composable
internal fun CountryPickerList(
    countries: List<Country>,
    selectedCountry: Country?,
    showDialCode: Boolean,
    showRegion: Boolean,
    onCountryClick: (Country) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalCountryPickerTheme.current
    val listState = rememberLazyListState()

    // Scroll to selected country when dialog opens
    LaunchedEffect(selectedCountry) {
        selectedCountry?.let { country ->
            val index = countries.indexOfFirst { it.iso2 == country.iso2 }
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = theme.dialogMaxHeight - 150.dp),
            verticalArrangement = Arrangement.spacedBy(theme.spacing.xSmall),
            contentPadding = PaddingValues(
                horizontal = theme.spacing.small,
                vertical = theme.spacing.small
            )
        ) {
            items(
                items = countries,
                key = { it.iso2 }
            ) { country ->
                CountryListItem(
                    country = country,
                    isSelected = country.iso2 == selectedCountry?.iso2,
                    showDialCode = showDialCode,
                    showRegion = showRegion,
                    onClick = { onCountryClick(country) }
                )
            }
        }

        // Scroll indicator (right side)
        if (theme.showScrollIndicator && countries.size > 8) {
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(4.dp),
                listState = listState
            )
        }
    }
}

/**
 * Enhanced Country List Item with selection animation
 *
 * Features:
 * - Smooth selection animation
 * - Ripple effect on tap
 * - Hover state (elevation change)
 * - Clear visual hierarchy
 */
@Composable
internal fun CountryListItem(
    country: Country,
    isSelected: Boolean,
    showDialCode: Boolean,
    showRegion: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalCountryPickerTheme.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(theme.itemHeight)
            .clickable(
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
                onClickLabel = "Select ${country.name}"
            ),
        shape = theme.itemShape,
        color = if (isSelected) theme.getContainerColor() else Color.Transparent,
        tonalElevation = if (isSelected) theme.itemElevation else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = theme.spacing.medium,
                    vertical = theme.spacing.small
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(theme.spacing.small)
        ) {
            // Flag emoji (large)
            Text(
                country.flagEmoji,
                style = theme.getTypography().headlineSmall,
                modifier = Modifier.width(36.dp)
            )

            // Country info (name and region)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    country.name,
                    style = theme.getTypography().bodyMedium,
                    color = if (isSelected) theme.getOnContainerColor()
                    else theme.getOnSurfaceColor(),
                    maxLines = 1
                )

                if (showRegion && country.region.isNotEmpty()) {
                    Text(
                        country.region,
                        style = theme.getTypography().labelSmall,
                        color = theme.getOnSurfaceColor()
                            .copy(alpha = theme.secondaryTextAlpha),
                        maxLines = 1
                    )
                }
            }

            // Dial code (right side)
            if (showDialCode) {
                Text(
                    country.dialCode,
                    style = theme.getTypography().labelMedium,
                    color = theme.getOutlineColor(),
                    modifier = Modifier.widthIn(min = 48.dp)
                )
            }

            // Selection indicator (checkmark)
            if (isSelected) {
                Text(
                    "✓",
                    style = theme.getTypography().headlineSmall,
                    color = theme.getPrimaryColor(),
                    modifier = Modifier.padding(start = theme.spacing.small)
                )
            }
        }
    }
}

/**
 * Vertical scrollbar for the country list
 * Shows position in the list
 */
@Composable
internal fun VerticalScrollbar(
    modifier: Modifier = Modifier,
    listState: LazyListState
) {
    val theme = LocalCountryPickerTheme.current

    val scrollFraction = if (listState.layoutInfo.totalItemsCount > 0) {
        listState.firstVisibleItemIndex.toFloat() / listState.layoutInfo.totalItemsCount
    } else {
        0f
    }

    Box(
        modifier = modifier
            .background(
                color = theme.getPrimaryColor().copy(alpha = 0.3f),
                shape = RoundedCornerShape(2.dp)
            )
            .padding(vertical = (scrollFraction * 100).dp)
    )
}