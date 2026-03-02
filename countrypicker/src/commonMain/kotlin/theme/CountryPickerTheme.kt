package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Comprehensive theming configuration for Country Picker.
 *
 * Supports Material 3 design system by default.
 * All colors are optional - if null, uses Material 3 defaults.
 *
 * Example:
 * ```
 * val theme = CountryPickerTheme(
 *     primaryColor = Color.Blue,
 *     dialogShape = RoundedCornerShape(20.dp),
 *     animationDuration = 400
 * )
 * ```
 */
data class CountryPickerTheme(
    // ===== COLORS =====
    /**
     * Primary color for buttons, selected items, focus states
     * If null, uses Material 3 primary color
     */
    val primaryColor: Color? = null,

    /**
     * Surface color for dialog background, list items
     * If null, uses Material 3 surface color
     */
    val surfaceColor: Color? = null,

    /**
     * Text color for primary content
     * If null, uses Material 3 onSurface color
     */
    val onSurfaceColor: Color? = null,

    /**
     * Background color for selected items
     * If null, uses Material 3 primaryContainer color
     */
    val containerColor: Color? = null,

    /**
     * Text color on selected items
     * If null, uses Material 3 onPrimaryContainer color
     */
    val onContainerColor: Color? = null,

    /**
     * Color for hints, secondary text, borders
     * If null, uses Material 3 outline color
     */
    val outlineColor: Color? = null,

    /**
     * Color for error states and validation errors
     * If null, uses Material 3 error color
     */
    val errorColor: Color? = null,

    // ===== TYPOGRAPHY =====
    /**
     * Material 3 typography for all text styles
     * If null, uses Material 3 default typography
     */
    val typography: Typography? = null,

    /**
     * Whether to use Material 3 typography or custom
     * If true, overrides typography and uses Material 3 defaults
     */
    val useMaterial3Typography: Boolean = true,

    // ===== SHAPES =====
    /**
     * Shape for the dialog container
     * Default: RoundedCornerShape(28.dp) - Material 3 extra large
     */
    val dialogShape: Shape = RoundedCornerShape(28.dp),

    /**
     * Shape for country list items
     * Default: RoundedCornerShape(8.dp) - Material 3 small
     */
    val itemShape: Shape = RoundedCornerShape(8.dp),

    /**
     * Shape for search input field
     * Default: RoundedCornerShape(12.dp) - Material 3 medium
     */
    val searchShape: Shape = RoundedCornerShape(12.dp),

    /**
     * Shape for buttons
     * Default: RoundedCornerShape(8.dp)
     */
    val buttonShape: Shape = RoundedCornerShape(8.dp),

    // ===== SPACING =====
    /**
     * Custom spacing values (margins, padding, gaps)
     * If null, uses default spacing
     */
    val spacing: Spacing = Spacing(),

    // ===== DIMENSIONS =====
    /**
     * Width of the dialog
     * Default: 400.dp - good for phones and tablets
     */
    val dialogWidth: Dp = 400.dp,

    /**
     * Maximum height of the dialog
     * Default: 600.dp
     */
    val dialogMaxHeight: Dp = 600.dp,

    /**
     * Height of each country list item
     * Default: 56.dp - Material 3 standard touch target
     */
    val itemHeight: Dp = 56.dp,

    /**
     * Height of search input
     * Default: 56.dp
     */
    val searchHeight: Dp = 56.dp,

    /**
     * Elevation/shadow for dialog
     * Default: 8.dp
     */
    val dialogElevation: Dp = 8.dp,

    /**
     * Elevation for list items on hover/press
     * Default: 2.dp
     */
    val itemElevation: Dp = 2.dp,

    /**
     * Elevation for buttons
     * Default: 0.dp (filled buttons have background, no shadow needed)
     */
    val buttonElevation: Dp = 0.dp,

    // ===== ANIMATIONS & INTERACTIONS =====
    /**
     * Duration for enter/exit animations (ms)
     * Default: 300ms
     */
    val animationDuration: Int = 300,

    /**
     * Duration for item selection animation (ms)
     * Default: 150ms
     */
    val itemSelectionDuration: Int = 150,

    /**
     * Duration for search debounce (ms)
     * Default: 300ms - user stops typing, search runs
     */
    val searchDebounceDuration: Int = 300,

    /**
     * Whether to enable animations (set to false for testing)
     * Default: true
     */
    val enableAnimations: Boolean = true,

    // ===== OPACITY & ALPHA =====
    /**
     * Alpha for disabled state
     * Default: 0.5f
     */
    val disabledAlpha: Float = 0.5f,

    /**
     * Alpha for secondary/hint text
     * Default: 0.6f
     */
    val secondaryTextAlpha: Float = 0.6f,

    /**
     * Alpha for borders/outlines
     * Default: 0.2f
     */
    val outlineAlpha: Float = 0.2f,

    /**
     * Alpha on hover state
     * Default: 0.08f
     */
    val hoverAlpha: Float = 0.08f,

    // ===== BEHAVIOR =====
    /**
     * Close dialog when country is selected
     * Default: true
     */
    val closeOnSelection: Boolean = true,

    /**
     * Dismiss on outside tap (overlay click)
     * Default: true
     */
    val dismissOnOutsideTap: Boolean = true,

    /**
     * Show scroll indicator in list
     * Default: true
     */
    val showScrollIndicator: Boolean = true,

    /**
     * Highlight search matches in country names
     * Default: true (future feature)
     */
    val highlightSearchMatches: Boolean = true
)

/**
 * Spacing configuration for padding, margins, gaps
 *
 * Example:
 * ```
 * Spacing(
 *     small = 6.dp,
 *     medium = 12.dp,
 *     large = 20.dp,
 *     xLarge = 28.dp
 * )
 * ```
 */
data class Spacing(
    /**
     * Tight spacing: 8.dp
     * Use for: internal element gaps, tight padding
     */
    val small: Dp = 8.dp,

    /**
     * Standard spacing: 16.dp
     * Use for: padding inside containers, gaps between elements
     */
    val medium: Dp = 16.dp,

    /**
     * Generous spacing: 24.dp
     * Use for: major section separation
     */
    val large: Dp = 24.dp,

    /**
     * Extra large spacing: 32.dp
     * Use for: dialog margins, major sections
     */
    val xLarge: Dp = 32.dp,

    /**
     * Dense spacing: 4.dp (for compact layouts)
     * Use for: dense lists, compact UI
     */
    val xSmall: Dp = 4.dp
)

/**
 * CompositionLocal to provide theme throughout the picker hierarchy
 *
 * Internal use only - automatically provided by CountryPickerDialog
 */
internal val LocalCountryPickerTheme = compositionLocalOf<CountryPickerTheme> {
    CountryPickerTheme()
}

/**
 * Get current CountryPickerTheme from CompositionLocal
 *
 * Usage:
 * ```
 * @Composable
 * fun MyComponent() {
 *     val theme = LocalCountryPickerTheme.current
 *     Text("Color: ${theme.getPrimaryColor()}")
 * }
 * ```
 */
@Composable
fun currentCountryPickerTheme(): CountryPickerTheme {
    val theme = LocalCountryPickerTheme.current
    return theme
}

// ===== COLOR RESOLVER EXTENSION FUNCTIONS =====

/**
 * Get primary color with Material 3 fallback
 * Safe to call in Composable context
 */
@Composable
fun CountryPickerTheme.getPrimaryColor(): Color =
    primaryColor ?: MaterialTheme.colorScheme.primary

/**
 * Get surface color with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getSurfaceColor(): Color =
    surfaceColor ?: MaterialTheme.colorScheme.surface

/**
 * Get onSurface color with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getOnSurfaceColor(): Color =
    onSurfaceColor ?: MaterialTheme.colorScheme.onSurface

/**
 * Get container color with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getContainerColor(): Color =
    containerColor ?: MaterialTheme.colorScheme.primaryContainer

/**
 * Get onContainer color with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getOnContainerColor(): Color =
    onContainerColor ?: MaterialTheme.colorScheme.onPrimaryContainer

/**
 * Get outline color with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getOutlineColor(): Color =
    outlineColor ?: MaterialTheme.colorScheme.outline

/**
 * Get error color with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getErrorColor(): Color =
    errorColor ?: MaterialTheme.colorScheme.error

/**
 * Get typography with Material 3 fallback
 */
@Composable
fun CountryPickerTheme.getTypography(): Typography =
    typography ?: MaterialTheme.typography

// ===== PRESET THEMES (Convenience Builders) =====

/**
 * Preset theme factory object
 *
 * Usage:
 * ```
 * val theme = CountryPickerThemeDefaults.light()
 * val theme = CountryPickerThemeDefaults.compact()
 * ```
 */
object CountryPickerThemeDefaults {
    /**
     * Light theme preset
     */
    fun light(): CountryPickerTheme =
        CountryPickerTheme(
            primaryColor = Color(0xFF6200EE),
            surfaceColor = Color(0xFFFFFFFF),
            onSurfaceColor = Color(0xFF000000),
            containerColor = Color(0xFFEADDFF),
            onContainerColor = Color(0xFF21005D)
        )

    /**
     * Dark theme preset
     */
    fun dark(): CountryPickerTheme =
        CountryPickerTheme(
            primaryColor = Color(0xFFD0BCFF),
            surfaceColor = Color(0xFF1C1B1F),
            onSurfaceColor = Color(0xFFE6E1E6),
            containerColor = Color(0xFF4F378B),
            onContainerColor = Color(0xFFF6EFFE)
        )

    /**
     * Minimal theme (no custom colors, pure Material 3)
     */
    fun material3(): CountryPickerTheme =
        CountryPickerTheme()

    /**
     * Compact theme (smaller sizes, dense spacing)
     */
    fun compact(): CountryPickerTheme =
        CountryPickerTheme(
            dialogWidth = 360.dp,
            dialogMaxHeight = 400.dp,
            itemHeight = 48.dp,
            spacing = Spacing(
                xSmall = 2.dp,
                small = 6.dp,
                medium = 12.dp,
                large = 18.dp,
                xLarge = 24.dp
            )
        )

    /**
     * Spacious theme (larger sizes, generous spacing)
     */
    fun spacious(): CountryPickerTheme =
        CountryPickerTheme(
            dialogWidth = 480.dp,
            dialogMaxHeight = 700.dp,
            itemHeight = 64.dp,
            spacing = Spacing(
                xSmall = 6.dp,
                small = 12.dp,
                medium = 20.dp,
                large = 32.dp,
                xLarge = 48.dp
            )
        )
}