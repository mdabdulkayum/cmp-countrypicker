package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.Country
import core.CountryFilter
import phone.PhoneFormatter
import phone.PhoneValidator
import theme.CountryPickerTheme
import theme.CountryPickerThemeDefaults
import theme.getErrorColor
import theme.getOnSurfaceColor
import theme.getOutlineColor
import theme.getSurfaceColor
import theme.getTypography

/**
 * Result of phone input field state
 */
data class PhoneInputState(
    val country: Country? = null,
    val phoneNumber: String = "",
    val formattedNumber: String = "",
    val isValid: Boolean = false,
    val error: String? = null,
    val shouldFormat: Boolean = false
) {
    /**
     * Get complete international phone number
     */
    fun getInternationalNumber(): String {
        if (country == null || phoneNumber.isEmpty()) return ""
        return country.dialCode + phoneNumber.filter { it.isDigit() }
    }
}

/**
 * Complete phone input field with country picker
 *
 * Features:
 * - Country selector (bottom sheet)
 * - Phone number input
 * - Real-time validation
 * - Optional formatting (user-controlled)
 * - Error messages
 * - Keyboard support
 *
 * Example:
 * ```
 * var phoneState by remember { mutableStateOf(PhoneInputState()) }
 *
 * PhoneInputField(
 *     state = phoneState,
 *     onStateChange = { phoneState = it },
 *     defaultCountry = CountryRepository.getByIso2("US")
 * )
 * ```
 */
@Composable
fun PhoneInputField(
    state: PhoneInputState,
    onStateChange: (PhoneInputState) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Phone Number",
    defaultCountry: Country? = null,
    countryFilter: CountryFilter = CountryFilter.All,
    allowFormatting: Boolean = false,
    theme: CountryPickerTheme = CountryPickerThemeDefaults.light(),
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = false,
    showCountryISO2: Boolean = false
) {
    var isCountryPickerOpen by remember { mutableStateOf(false) }
    val showFormatToggle by remember { mutableStateOf(allowFormatting) }

    val currentCountry = state.country ?: defaultCountry

    val pickerState = rememberCountryPickerState(
        defaultCountry = currentCountry,
        countryFilter = countryFilter
    )

    LaunchedEffect(currentCountry) {
        currentCountry?.let { pickerState.selectCountry(it) }
    }

    Column(modifier = modifier) {
        // Row: Country selector + Phone number input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (state.error != null) theme.getErrorColor() else theme.getOutlineColor(),
                    shape = theme.itemShape
                )
                .background(
                    color = theme.getSurfaceColor(),
                    shape = theme.itemShape
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country selector button
            CountrySelectButton(
                country = currentCountry,
                onClick = { isCountryPickerOpen = true },
                theme = theme,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .wrapContentWidth(),
                showCountryCode = showCountryCode,
                showCountryFlag = showCountryFlag,
                showCountryISO2 = showCountryISO2,
            )

            // Divider
            VerticalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .height(36.dp),
                color = theme.getOutlineColor().copy(alpha = 0.2f)
            )

            // Phone number input
            PhoneNumberInput(
                value = state.phoneNumber,
                onValueChange = { newNumber ->
                    val cleaned = newNumber.filter { it.isDigit() }

                    // Validate
                    val validation = if (currentCountry != null) {
                        PhoneValidator.validateByCountry(currentCountry, cleaned)
                    } else {
                        null
                    }

                    // Format if enabled
                    val formatted = if (showFormatToggle && state.shouldFormat && currentCountry != null) {
                        PhoneFormatter.format(
                            countryCode = currentCountry.iso2,
                            nationalNumber = cleaned,
                            shouldFormat = true
                        )
                    } else {
                        cleaned
                    }

                    onStateChange(
                        state.copy(
                            phoneNumber = cleaned,
                            formattedNumber = formatted,
                            isValid = validation?.isValid ?: false,
                            error = validation?.error,
                            country = currentCountry
                        )
                    )
                },
                label = label,
                country = currentCountry,
                isValid = state.isValid,
                theme = theme,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
        }

        // Error message
        if (state.error != null && state.phoneNumber.isNotEmpty()) {
            Text(
                text = state.error,
                style = theme.getTypography().labelSmall,
                color = theme.getErrorColor(),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Format toggle (if allowed and country selected)

        if (allowFormatting && currentCountry != null && state.phoneNumber.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Format number",
                    style = theme.getTypography().labelSmall,
                    color = theme.getOnSurfaceColor().copy(alpha = 0.7f)
                )

                Switch(
                    checked = state.shouldFormat,
                    onCheckedChange = { shouldFormat ->
                        onStateChange(
                            state.copy(shouldFormat = shouldFormat)
                        )
                    },
                    modifier = Modifier.scale(0.8f),
                    enabled = state.isValid
                )
            }
        }

        // Formatted display
        if (state.shouldFormat && state.phoneNumber.isNotEmpty() && currentCountry != null) {
            Text(
                text = "Formatted: ${state.formattedNumber}",
                style = theme.getTypography().labelSmall,
                color = theme.getOnSurfaceColor().copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

    }

    // Country picker bottom sheet
    CountryPickerBottomSheet(
        state = pickerState,
        isOpen = isCountryPickerOpen,
        onDismiss = { isCountryPickerOpen = false },
        onCountrySelected = { country ->
            onStateChange(
                state.copy(
                    country = country,
                    error = null  // Clear error when country changes
                )
            )
            isCountryPickerOpen = false // Manually close after selection
        },
        theme = theme,
        showDialCode = showCountryCode
    )
}

/**
 * Country selector button
 */
@Composable
private fun CountrySelectButton(
    country: Country?,
    onClick: () -> Unit,
    theme: CountryPickerTheme,
    modifier: Modifier = Modifier,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = false,
    showCountryISO2: Boolean = false
) {
    Row(
        modifier = modifier
            .clickable(
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if(showCountryFlag){
            Text(
                text = country?.flagEmoji ?: "🌍",
                style = theme.getTypography().headlineSmall
            )
        }

        if(showCountryISO2){
            Text(
                text = country?.iso2 ?: "",
                style = theme.getTypography().labelSmall,
                color = theme.getOnSurfaceColor(),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        if(showCountryCode){
            Text(
                text = country?.dialCode ?: "Select",
                style = theme.getTypography().labelSmall,
                color = theme.getOnSurfaceColor(),
                modifier = Modifier.padding(start = 4.dp)
            )
        }


    }
}

/**
 * Phone number input field
 */
@Composable
private fun PhoneNumberInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    country: Country?,
    isValid: Boolean,
    theme: CountryPickerTheme,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            // Only allow digits
            val digits = newValue.filter { it.isDigit() }
            if (digits.length <= 15) {  // Max 15 digits for international numbers
                onValueChange(digits)
            }
        },
        modifier = modifier
            .height(56.dp),
        placeholder = {
            Text(
                text = "Enter number",
                style = theme.getTypography().bodyMedium,
                color = theme.getOnSurfaceColor().copy(alpha = 0.5f)
            )
        },
        singleLine = true,
        textStyle = theme.getTypography().bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = theme.getOnSurfaceColor(),
            unfocusedTextColor = theme.getOnSurfaceColor()
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { /* Dismiss keyboard */ }
        ),
        /*
        trailingIcon = {
            if (value.isNotEmpty()) {
                if (isValid) {
                    Text(
                        "✓",
                        style = theme.getTypography().headlineSmall,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                } else if (country != null) {
                    Text(
                        "✗",
                        style = theme.getTypography().headlineSmall,
                        color = theme.getErrorColor(),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }
        }
        */
    )
}
