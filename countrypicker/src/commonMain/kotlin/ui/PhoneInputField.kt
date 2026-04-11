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
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import core.Country
import core.CountryFilter
import core.internal.CountryData
import phone.PhoneFormatter
import phone.PhoneValidator
import theme.CountryPickerTheme
import theme.CountryPickerThemeDefaults
import theme.getErrorColor
import theme.getOnSurfaceColor
import theme.getOutlineColor
import theme.getPrimaryColor
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

    companion object {
        fun fromFullNumber(fullNumber: String): PhoneInputState {
            val (country, nationalNumber) = CountryData.parsePhoneNumber(fullNumber)

            // Optional: Run initial validation
            val validation = country?.let {
                PhoneValidator.validateByCountry(it, nationalNumber)
            }

            return PhoneInputState(
                phoneNumber = nationalNumber,
                country = country,
                isValid = validation?.isValid ?: false,
                error = validation?.error
            )
        }
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
    showCountryISO2: Boolean = false,
    isEnable: Boolean = true,
    showTrail: Boolean = false,
    trailText: String = "Search",
    onTrailClick: () -> Unit = {}
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
                isEnable = isEnable
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

                    // 1. If it's just a "+", keep it in the state so the user can continue typing
                    if (newNumber == "+") {
                        onStateChange(state.copy(phoneNumber = "+", isValid = false, error = null))
                        return@PhoneNumberInput
                    }

                    // 2. If it starts with "+" and has more digits, try to parse
                    if (newNumber.startsWith("+") && newNumber.length > 1) {
                        val (parsedCountry, nationalNumber) = CountryData.parsePhoneNumber(newNumber)

                        if (parsedCountry != null) {
                            // We found a match! Update country and strip the dial code
                            val validation =
                                PhoneValidator.validateByCountry(parsedCountry, nationalNumber)
                            onStateChange(
                                state.copy(
                                    country = parsedCountry,
                                    phoneNumber = nationalNumber,
                                    isValid = validation.isValid,
                                    error = validation.error
                                )
                            )
                            return@PhoneNumberInput
                        } else {
                            // Still typing the dial code (e.g., "+88"), keep the raw input for now
                            onStateChange(state.copy(phoneNumber = newNumber, isValid = false))
                            return@PhoneNumberInput
                        }
                    }

                    // 2. Standard digit-only cleaning
                    val cleaned = newNumber.filter { it.isDigit() }

                    // 3. Apply Max Length based on the current country
                    // Using 15 as a safe fallback for international standards
                    val maxLength = currentCountry?.phoneNumberLength?.last ?: 15
                    if (cleaned.length > maxLength) return@PhoneNumberInput

                    // 4. Validate and Update
                    val validation = if (currentCountry != null) {
                        PhoneValidator.validateByCountry(currentCountry, cleaned)
                    } else null

                    val formatted =
                        if (showFormatToggle && state.shouldFormat && currentCountry != null) {
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
                isEnable = isEnable,
                showTrial = showTrail,
                trailText = trailText,
                onTrailClick = onTrailClick,
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
    showCountryISO2: Boolean = false,
    isEnable: Boolean = true
) {
    Row(
        modifier = modifier
            .clickable(
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() },
                enabled = isEnable,
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if (showCountryFlag) {
            Text(
                text = country?.flagEmoji ?: "🌍",
                style = theme.getTypography().headlineSmall
            )
        }

        if (showCountryISO2) {
            Text(
                text = country?.iso2 ?: "",
                style = theme.getTypography().labelSmall,
                color = theme.getOnSurfaceColor(),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        if (showCountryCode) {
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
    isEnable: Boolean = true,
    showTrial: Boolean,
    trailText: String,
    onTrailClick: () -> Unit,
    theme: CountryPickerTheme,
    modifier: Modifier = Modifier
) {

    val customTextSelectionColors = TextSelectionColors(
        handleColor = theme.getPrimaryColor(),
        backgroundColor = theme.getPrimaryColor().copy(alpha = 0.2f)
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    val displayLabel = remember(label) {
        if (label.contains("*")) buildRequiredLabel(label)
        else AnnotatedString(label)
    }

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                // Allow digits AND the '+' sign at the start
                val allowedChars = newValue.filterIndexed { index, char ->
                    char.isDigit() || (char == '+' && index == 0)
                }

                if (allowedChars.length <= 16) {
                    onValueChange(allowedChars)
                }

            },
            modifier = modifier
                .height(56.dp),
            placeholder = {
                Text(
                    text = displayLabel,
                    style = theme.getTypography().bodyMedium,
                    color = theme.getOnSurfaceColor().copy(alpha = 0.5f)
                )
            },
            enabled = isEnable,
            singleLine = true,
            textStyle = theme.getTypography().bodyMedium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,

                disabledTextColor = theme.getOnSurfaceColor().copy(alpha = 0.38f),
                disabledPlaceholderColor = theme.getOnSurfaceColor().copy(alpha = 0.38f),
                disabledLabelColor = theme.getOnSurfaceColor().copy(alpha = 0.38f),

                cursorColor = theme.getOutlineColor(),
                focusedTextColor = theme.getOnSurfaceColor(),
                unfocusedTextColor = theme.getOnSurfaceColor()
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),

            trailingIcon = {
                if (showTrial) {
                    Text(
                        text = trailText,
                        style = theme.getTypography().bodySmall,
                        color = if(isEnable) theme.getPrimaryColor() else theme.getOutlineColor(),
                        modifier = Modifier.padding(end = 8.dp)
                            .clickable(isEnable) { onTrailClick() }
                    )
                }

            }

        )
    }

}


fun buildRequiredLabel(label: String): AnnotatedString {
    return buildAnnotatedString {
        append(label.replace("*", "").trim()) // Append the main text
        append(" ")
        withStyle(style = SpanStyle(color = Color.Red)) {
            append("*") // Append the red asterisk
        }
    }
}