package core.internal

import core.Country

internal object CountryData {

    val countries: List<Country> = listOf(
        // Asia (51 countries)
        Country("Afghanistan", "AF", "AFG", "+93", "🇦🇫", "Asia", 9..9),
        Country("Armenia", "AM", "ARM", "+374", "🇦🇲", "Asia", 8..8),
        Country("Azerbaijan", "AZ", "AZE", "+994", "🇦🇿", "Asia", 9..9),
        Country("Bahrain", "BH", "BHR", "+973", "🇧🇭", "Asia", 8..8),
        Country("Bangladesh", "BD", "BGD", "+880", "🇧🇩", "Asia", 10..10, customRegex = Regex("^1[3-9]\\d{8}$"), isDefault = true),
        Country("Bhutan", "BT", "BTN", "+975", "🇧🇹", "Asia", 8..8),
        Country("Brunei", "BN", "BRN", "+673", "🇧🇳", "Asia", 7..7),
        Country("Cambodia", "KH", "KHM", "+855", "🇰🇭", "Asia", 8..9),
        Country("China", "CN", "CHN", "+86", "🇨🇳", "Asia", 11..11, customRegex = Regex("^1[3-9]\\d{9}$")),
        Country("Georgia", "GE", "GEO", "+995", "🇬🇪", "Asia", 9..9),
        Country("Hong Kong", "HK", "HKG", "+852", "🇭🇰", "Asia", 8..8),
        Country("India", "IN", "IND", "+91", "🇮🇳", "Asia", 10..10, customRegex = Regex("^[6-9]\\d{9}$")),
        Country("Indonesia", "ID", "IDN", "+62", "🇮🇩", "Asia", 9..12),
        Country("Iran", "IR", "IRN", "+98", "🇮🇷", "Asia", 10..10),
        Country("Iraq", "IQ", "IRQ", "+964", "🇮🇶", "Asia", 10..10),
        Country("Israel", "IL", "ISR", "+972", "🇮🇱", "Asia", 9..9),
        Country("Japan", "JP", "JPN", "+81", "🇯🇵", "Asia", 10..11),
        Country("Jordan", "JO", "JOR", "+962", "🇯🇴", "Asia", 9..9),
        Country("Kazakhstan", "KZ", "KAZ", "+7", "🇰🇿", "Asia", 10..10),
        Country("North Korea", "KP", "PRK", "+850", "🇰🇵", "Asia", 8..8),
        Country("South Korea", "KR", "KOR", "+82", "🇰🇷", "Asia", 10..11),
        Country("Kuwait", "KW", "KWT", "+965", "🇰🇼", "Asia", 8..8),
        Country("Kyrgyzstan", "KG", "KGZ", "+996", "🇰🇬", "Asia", 9..9),
        Country("Laos", "LA", "LAO", "+856", "🇱🇦", "Asia", 8..9),
        Country("Lebanon", "LB", "LBN", "+961", "🇱🇧", "Asia", 8..8),
        Country("Macau", "MO", "MAC", "+853", "🇲🇴", "Asia", 8..8),
        Country("Malaysia", "MY", "MYS", "+60", "🇲🇾", "Asia", 9..10),
        Country("Maldives", "MV", "MDV", "+960", "🇲🇻", "Asia", 7..7),
        Country("Mongolia", "MN", "MNG", "+976", "🇲🇳", "Asia", 8..8),
        Country("Myanmar", "MM", "MMR", "+95", "🇲🇲", "Asia", 8..9),
        Country("Nepal", "NP", "NPL", "+977", "🇳🇵", "Asia", 10..10),
        Country("Oman", "OM", "OMN", "+968", "🇴🇲", "Asia", 8..8),
        Country("Pakistan", "PK", "PAK", "+92", "🇵🇰", "Asia", 10..10, customRegex = Regex("^3\\d{9}$")),
        Country("Palestine", "PS", "PSE", "+970", "🇵🇸", "Asia", 9..9),
        Country("Philippines", "PH", "PHL", "+63", "🇵🇭", "Asia", 10..10),
        Country("Qatar", "QA", "QAT", "+974", "🇶🇦", "Asia", 8..8),
        Country("Saudi Arabia", "SA", "SAU", "+966", "🇸🇦", "Asia", 9..9),
        Country("Singapore", "SG", "SGP", "+65", "🇸🇬", "Asia", 8..8, customRegex = Regex("^[689]\\d{7}$")),
        Country("Sri Lanka", "LK", "LKA", "+94", "🇱🇰", "Asia", 9..9),
        Country("Syria", "SY", "SYR", "+963", "🇸🇾", "Asia", 9..9),
        Country("Taiwan", "TW", "TWN", "+886", "🇹🇼", "Asia", 9..10),
        Country("Tajikistan", "TJ", "TJK", "+992", "🇹🇯", "Asia", 9..9),
        Country("Thailand", "TH", "THA", "+66", "🇹🇭", "Asia", 9..10),
        Country("East Timor", "TL", "TLS", "+670", "🇹🇱", "Asia", 7..8),
        Country("Turkey", "TR", "TUR", "+90", "🇹🇷", "Asia", 10..10),
        Country("Turkmenistan", "TM", "TKM", "+993", "🇹🇲", "Asia", 8..8),
        Country("United Arab Emirates", "AE", "ARE", "+971", "🇦🇪", "Asia", 9..9),
        Country("Uzbekistan", "UZ", "UZB", "+998", "🇺🇿", "Asia", 9..9),
        Country("Vietnam", "VN", "VNM", "+84", "🇻🇳", "Asia", 9..10),
        Country("Yemen", "YE", "YEM", "+967", "🇾🇪", "Asia", 9..9),

        // Europe (50 countries)
        Country("Albania", "AL", "ALB", "+355", "🇦🇱", "Europe", 9..9),
        Country("Andorra", "AD", "AND", "+376", "🇦🇩", "Europe", 6..6),
        Country("Austria", "AT", "AUT", "+43", "🇦🇹", "Europe", 10..11),
        Country("Belarus", "BY", "BLR", "+375", "🇧🇾", "Europe", 9..9),
        Country("Belgium", "BE", "BEL", "+32", "🇧🇪", "Europe", 9..9),
        Country("Bosnia and Herzegovina", "BA", "BIH", "+387", "🇧🇦", "Europe", 8..8),
        Country("Bulgaria", "BG", "BGR", "+359", "🇧🇬", "Europe", 9..9),
        Country("Croatia", "HR", "HRV", "+385", "🇭🇷", "Europe", 9..9),
        Country("Cyprus", "CY", "CYP", "+357", "🇨🇾", "Europe", 8..8),
        Country("Czech Republic", "CZ", "CZE", "+420", "🇨🇿", "Europe", 9..9),
        Country("Denmark", "DK", "DNK", "+45", "🇩🇰", "Europe", 8..8),
        Country("Estonia", "EE", "EST", "+372", "🇪🇪", "Europe", 8..8),
        Country("Finland", "FI", "FIN", "+358", "🇫🇮", "Europe", 9..9),
        Country("France", "FR", "FRA", "+33", "🇫🇷", "Europe", 9..10),
        Country("Germany", "DE", "DEU", "+49", "🇩🇪", "Europe", 10..11),
        Country("Greece", "GR", "GRC", "+30", "🇬🇷", "Europe", 10..10),
        Country("Hungary", "HU", "HUN", "+36", "🇭🇺", "Europe", 9..9),
        Country("Iceland", "IS", "ISL", "+354", "🇮🇸", "Europe", 7..7),
        Country("Ireland", "IE", "IRL", "+353", "🇮🇪", "Europe", 9..9),
        Country("Italy", "IT", "ITA", "+39", "🇮🇹", "Europe", 10..10),
        Country("Kosovo", "XK", "KOS", "+383", "🇽🇰", "Europe", 8..8),
        Country("Latvia", "LV", "LVA", "+371", "🇱🇻", "Europe", 8..8),
        Country("Liechtenstein", "LI", "LIE", "+423", "🇱🇮", "Europe", 7..7),
        Country("Lithuania", "LT", "LTU", "+370", "🇱🇹", "Europe", 8..8),
        Country("Luxembourg", "LU", "LUX", "+352", "🇱🇺", "Europe", 9..9),
        Country("Malta", "MT", "MLT", "+356", "🇲🇹", "Europe", 8..8),
        Country("Moldova", "MD", "MDA", "+373", "🇲🇩", "Europe", 8..8),
        Country("Monaco", "MC", "MCO", "+377", "🇲🇨", "Europe", 8..8),
        Country("Montenegro", "ME", "MNE", "+382", "🇲🇪", "Europe", 8..8),
        Country("Netherlands", "NL", "NLD", "+31", "🇳🇱", "Europe", 9..9),
        Country("North Macedonia", "MK", "MKD", "+389", "🇲🇰", "Europe", 8..8),
        Country("Norway", "NO", "NOR", "+47", "🇳🇴", "Europe", 8..8),
        Country("Poland", "PL", "POL", "+48", "🇵🇱", "Europe", 9..9),
        Country("Portugal", "PT", "PRT", "+351", "🇵🇹", "Europe", 9..9),
        Country("Romania", "RO", "ROU", "+40", "🇷🇴", "Europe", 9..9),
        Country("Russia", "RU", "RUS", "+7", "🇷🇺", "Europe", 10..10),
        Country("San Marino", "SM", "SMR", "+378", "🇸🇲", "Europe", 10..10),
        Country("Serbia", "RS", "SRB", "+381", "🇷🇸", "Europe", 9..9),
        Country("Slovakia", "SK", "SVK", "+421", "🇸🇰", "Europe", 9..9),
        Country("Slovenia", "SI", "SVN", "+386", "🇸🇮", "Europe", 8..8),
        Country("Spain", "ES", "ESP", "+34", "🇪🇸", "Europe", 9..9),
        Country("Sweden", "SE", "SWE", "+46", "🇸🇪", "Europe", 9..9),
        Country("Switzerland", "CH", "CHE", "+41", "🇨🇭", "Europe", 9..9),
        Country("Ukraine", "UA", "UKR", "+380", "🇺🇦", "Europe", 9..9),
        Country("United Kingdom", "GB", "GBR", "+44", "🇬🇧", "Europe", 10..11, customRegex = Regex("^7\\d{9}$")),
        Country("Vatican City", "VA", "VAT", "+379", "🇻🇦", "Europe", 10..10),

        // North America (23 countries)
        Country("Antigua and Barbuda", "AG", "ATG", "+1-268", "🇦🇬", "North America", 10..10),
        Country("Bahamas", "BS", "BHS", "+1-242", "🇧🇸", "North America", 10..10),
        Country("Barbados", "BB", "BRB", "+1-246", "🇧🇧", "North America", 10..10),
        Country("Belize", "BZ", "BLZ", "+501", "🇧🇿", "North America", 7..7),
        Country("Canada", "CA", "CAN", "+1", "🇨🇦", "North America", 10..10, customRegex = Regex("^[2-9]\\d{9}$")),
        Country("Costa Rica", "CR", "CRI", "+506", "🇨🇷", "North America", 8..8),
        Country("Cuba", "CU", "CUB", "+53", "🇨🇺", "North America", 8..8),
        Country("Dominica", "DM", "DMA", "+1-767", "🇩🇲", "North America", 10..10),
        Country("Dominican Republic", "DO", "DOM", "+1-809", "🇩🇴", "North America", 10..10),
        Country("El Salvador", "SV", "SLV", "+503", "🇸🇻", "North America", 8..8),
        Country("Grenada", "GD", "GRD", "+1-473", "🇬🇩", "North America", 10..10),
        Country("Guatemala", "GT", "GTM", "+502", "🇬🇹", "North America", 8..8),
        Country("Haiti", "HT", "HTI", "+509", "🇭🇹", "North America", 8..8),
        Country("Honduras", "HN", "HND", "+504", "🇭🇳", "North America", 8..8),
        Country("Jamaica", "JM", "JAM", "+1-876", "🇯🇲", "North America", 10..10),
        Country("Mexico", "MX", "MEX", "+52", "🇲🇽", "North America", 10..11),
        Country("Nicaragua", "NI", "NIC", "+505", "🇳🇮", "North America", 8..8),
        Country("Panama", "PA", "PAN", "+507", "🇵🇦", "North America", 8..8),
        Country("Saint Kitts and Nevis", "KN", "KNA", "+1-869", "🇰🇳", "North America", 10..10),
        Country("Saint Lucia", "LC", "LCA", "+1-758", "🇱🇨", "North America", 10..10),
        Country("Saint Vincent and the Grenadines", "VC", "VCT", "+1-784", "🇻🇨", "North America", 10..10),
        Country("Trinidad and Tobago", "TT", "TTO", "+1-868", "🇹🇹", "North America", 10..10),
        Country("United States", "US", "USA", "+1", "🇺🇸", "North America", 10..11, customRegex = Regex("^[2-9]\\d{9}$")),

        // South America (13 countries)
        Country("Argentina", "AR", "ARG", "+54", "🇦🇷", "South America", 10..11),
        Country("Bolivia", "BO", "BOL", "+591", "🇧🇴", "South America", 8..8),
        Country("Brazil", "BR", "BRA", "+55", "🇧🇷", "South America", 11..11),
        Country("Chile", "CL", "CHL", "+56", "🇨🇱", "South America", 9..9),
        Country("Colombia", "CO", "COL", "+57", "🇨🇴", "South America", 10..10),
        Country("Ecuador", "EC", "ECU", "+593", "🇪🇨", "South America", 9..9),
        Country("Guyana", "GY", "GUY", "+592", "🇬🇾", "South America", 7..7),
        Country("Paraguay", "PY", "PRY", "+595", "🇵🇾", "South America", 9..9),
        Country("Peru", "PE", "PER", "+51", "🇵🇪", "South America", 9..9),
        Country("Suriname", "SR", "SUR", "+597", "🇸🇷", "South America", 7..7),
        Country("Uruguay", "UY", "URY", "+598", "🇺🇾", "South America", 8..9),
        Country("Venezuela", "VE", "VEN", "+58", "🇻🇪", "South America", 10..10),

        // Oceania (14 countries)
        Country("Australia", "AU", "AUS", "+61", "🇦🇺", "Oceania", 9..9),
        Country("Fiji", "FJ", "FJI", "+679", "🇫🇯", "Oceania", 7..7),
        Country("Kiribati", "KI", "KIR", "+686", "🇰🇮", "Oceania", 5..5),
        Country("Marshall Islands", "MH", "MHL", "+692", "🇲🇭", "Oceania", 7..7),
        Country("Micronesia", "FM", "FSM", "+691", "🇫🇲", "Oceania", 7..7),
        Country("Nauru", "NR", "NRU", "+674", "🇳🇷", "Oceania", 7..7),
        Country("New Zealand", "NZ", "NZL", "+64", "🇳🇿", "Oceania", 9..9),
        Country("Palau", "PW", "PLW", "+680", "🇵🇼", "Oceania", 7..7),
        Country("Papua New Guinea", "PG", "PNG", "+675", "🇵🇬", "Oceania", 8..8),
        Country("Samoa", "WS", "WSM", "+685", "🇼🇸", "Oceania", 7..7),
        Country("Solomon Islands", "SB", "SLB", "+677", "🇸🇧", "Oceania", 5..7),
        Country("Tonga", "TO", "TON", "+676", "🇹🇴", "Oceania", 5..5),
        Country("Tuvalu", "TV", "TUV", "+688", "🇹🇻", "Oceania", 6..6),
        Country("Vanuatu", "VU", "VUT", "+678", "🇻🇺", "Oceania", 7..7),

        // Africa (54 countries)
        Country("Algeria", "DZ", "DZA", "+213", "🇩🇿", "Africa", 9..9),
        Country("Angola", "AO", "AGO", "+244", "🇦🇴", "Africa", 9..9),
        Country("Benin", "BJ", "BEN", "+229", "🇧🇯", "Africa", 8..8),
        Country("Botswana", "BW", "BWA", "+267", "🇧🇼", "Africa", 8..8),
        Country("Burkina Faso", "BF", "BFA", "+226", "🇧🇫", "Africa", 8..8),
        Country("Burundi", "BI", "BDI", "+257", "🇧🇮", "Africa", 8..8),
        Country("Cameroon", "CM", "CMR", "+237", "🇨🇲", "Africa", 9..9),
        Country("Cape Verde", "CV", "CPV", "+238", "🇨🇻", "Africa", 7..7),
        Country("Central African Republic", "CF", "CAF", "+236", "🇨🇫", "Africa", 8..8),
        Country("Chad", "TD", "TCD", "+235", "🇹🇩", "Africa", 8..8),
        Country("Comoros", "KM", "COM", "+269", "🇰🇲", "Africa", 7..7),
        Country("Congo", "CG", "COG", "+242", "🇨🇬", "Africa", 9..9),
        Country("Democratic Republic of the Congo", "CD", "COD", "+243", "🇨🇩", "Africa", 9..9),
        Country("Djibouti", "DJ", "DJI", "+253", "🇩🇯", "Africa", 8..8),
        Country("Egypt", "EG", "EGY", "+20", "🇪🇬", "Africa", 10..10),
        Country("Equatorial Guinea", "GQ", "GNQ", "+240", "🇬🇶", "Africa", 9..9),
        Country("Eritrea", "ER", "ERI", "+291", "🇪🇷", "Africa", 7..7),
        Country("Eswatini", "SZ", "SWZ", "+268", "🇸🇿", "Africa", 8..8),
        Country("Ethiopia", "ET", "ETH", "+251", "🇪🇹", "Africa", 9..9),
        Country("Gabon", "GA", "GAB", "+241", "🇬🇦", "Africa", 8..8),
        Country("Gambia", "GM", "GMB", "+220", "🇬🇲", "Africa", 7..7),
        Country("Ghana", "GH", "GHA", "+233", "🇬🇭", "Africa", 9..9),
        Country("Guinea", "GN", "GIN", "+224", "🇬🇳", "Africa", 9..9),
        Country("Guinea-Bissau", "GW", "GNB", "+245", "🇬🇼", "Africa", 7..7),
        Country("Ivory Coast", "CI", "CIV", "+225", "🇨🇮", "Africa", 8..8),
        Country("Kenya", "KE", "KEN", "+254", "🇰🇪", "Africa", 9..9),
        Country("Lesotho", "LS", "LSO", "+266", "🇱🇸", "Africa", 8..8),
        Country("Liberia", "LR", "LBR", "+231", "🇱🇷", "Africa", 7..8),
        Country("Libya", "LY", "LBY", "+218", "🇱🇾", "Africa", 9..9),
        Country("Madagascar", "MG", "MDG", "+261", "🇲🇬", "Africa", 9..9),
        Country("Malawi", "MW", "MWI", "+265", "🇲🇼", "Africa", 9..9),
        Country("Mali", "ML", "MLI", "+223", "🇲🇱", "Africa", 8..8),
        Country("Mauritania", "MR", "MRT", "+222", "🇲🇷", "Africa", 8..8),
        Country("Mauritius", "MU", "MUS", "+230", "🇲🇺", "Africa", 8..8),
        Country("Morocco", "MA", "MAR", "+212", "🇲🇦", "Africa", 9..9),
        Country("Mozambique", "MZ", "MOZ", "+258", "🇲🇿", "Africa", 9..9),
        Country("Namibia", "NA", "NAM", "+264", "🇳🇦", "Africa", 9..9),
        Country("Niger", "NE", "NER", "+227", "🇳🇪", "Africa", 8..8),
        Country("Nigeria", "NG", "NGA", "+234", "🇳🇬", "Africa", 10..10, customRegex = Regex("^[7-9]\\d{9}$")),
        Country("Rwanda", "RW", "RWA", "+250", "🇷🇼", "Africa", 9..9),
        Country("Sao Tome and Principe", "ST", "STP", "+239", "🇸🇹", "Africa", 7..7),
        Country("Senegal", "SN", "SEN", "+221", "🇸🇳", "Africa", 9..9),
        Country("Seychelles", "SC", "SYC", "+248", "🇸🇨", "Africa", 7..7),
        Country("Sierra Leone", "SL", "SLE", "+232", "🇸🇱", "Africa", 8..8),
        Country("Somalia", "SO", "SOM", "+252", "🇸🇴", "Africa", 8..9),
        Country("South Africa", "ZA", "ZAF", "+27", "🇿🇦", "Africa", 9..9),
        Country("South Sudan", "SS", "SSD", "+211", "🇸🇸", "Africa", 9..9),
        Country("Sudan", "SD", "SDN", "+249", "🇸🇩", "Africa", 9..9),
        Country("Tanzania", "TZ", "TZA", "+255", "🇹🇿", "Africa", 9..9),
        Country("Togo", "TG", "TGO", "+228", "🇹🇬", "Africa", 8..8),
        Country("Tunisia", "TN", "TUN", "+216", "🇹🇳", "Africa", 8..8),
        Country("Uganda", "UG", "UGA", "+256", "🇺🇬", "Africa", 9..9),
        Country("Zambia", "ZM", "ZMB", "+260", "🇿🇲", "Africa", 9..9),
        Country("Zimbabwe", "ZW", "ZWE", "+263", "🇿🇼", "Africa", 9..9),


        // Island Territories & Special Regions
        Country("Aland Islands", "AX", "ALA", "+358", "🇦🇽", "Europe", 9..9),
        Country("American Samoa", "AS", "ASM", "+1-684", "🇦🇸", "Oceania", 10..10),
        Country("Anguilla", "AI", "AIA", "+1-264", "🇦🇮", "North America", 10..10),
        Country("Aruba", "AW", "ABW", "+297", "🇦🇼", "North America", 7..7),
        Country("Bermuda", "BM", "BMU", "+1-441", "🇧🇲", "North America", 10..10),
        Country("Bonaire", "BQ", "BES", "+599", "🇧🇶", "North America", 7..7),
        Country("British Indian Ocean Territory", "IO", "IOT", "+246", "🇮🇴", "Asia", 7..7),
        Country("British Virgin Islands", "VG", "VGB", "+1-284", "🇻🇬", "North America", 10..10),
        Country("Cayman Islands", "KY", "CYM", "+1-345", "🇰🇾", "North America", 10..10),
        Country("Christmas Island", "CX", "CXR", "+61", "🇨🇽", "Oceania", 8..8),
        Country("Cocos Islands", "CC", "CCK", "+61", "🇨🇨", "Oceania", 8..8),
        Country("Cook Islands", "CK", "COK", "+682", "🇨🇰", "Oceania", 5..5),
        Country("Curacao", "CW", "CUW", "+599", "🇨🇼", "North America", 7..7),
        Country("Falkland Islands", "FK", "FLK", "+500", "🇫🇰", "South America", 5..5),
        Country("Faroe Islands", "FO", "FRO", "+298", "🇫🇴", "Europe", 6..6),
        Country("French Guiana", "GF", "GUF", "+594", "🇬🇫", "South America", 9..9),
        Country("French Polynesia", "PF", "PYF", "+689", "🇵🇫", "Oceania", 8..8),
        Country("Gilbraltar", "GI", "GIB", "+350", "🇬🇮", "Europe", 8..8),
        Country("Greenland", "GL", "GRL", "+299", "🇬🇱", "North America", 6..6),
        Country("Guadeloupe", "GP", "GLP", "+590", "🇬🇵", "North America", 9..9),
        Country("Guam", "GU", "GUM", "+1-671", "🇬🇺", "Oceania", 10..10),
        Country("Isle of Man", "IM", "IMN", "+44", "🇮🇲", "Europe", 10..10),
        Country("Jersey", "JE", "JEY", "+44", "🇯🇪", "Europe", 10..10),
        Country("Guernsey", "GG", "GGY", "+44", "🇬🇬", "Europe", 10..10),
        Country("Martinique", "MQ", "MTQ", "+596", "🇲🇶", "North America", 9..9),
        Country("Mayotte", "YT", "MYT", "+262", "🇾🇹", "Africa", 9..9),
        Country("Montserrat", "MS", "MSR", "+1-664", "🇲🇸", "North America", 10..10),
        Country("New Caledonia", "NC", "NCL", "+687", "🇳🇨", "Oceania", 6..6),
    )


    /**
     * Lazy-loaded map for O(1) ISO2 lookups
     */
    private val iso2Map: Map<String, Country> by lazy {
        countries.associateBy { it.iso2 }
    }

    /**
     * Lazy-loaded map for dial code lookups
     */
    private val dialCodeMap: Map<String, List<Country>> by lazy {
        countries.groupBy { it.dialCode }
    }

    /**
     * Get country by ISO-2 code
     */
    fun getByIso2(code: String): Country? = iso2Map[code.uppercase()]

    /**
     * Get all countries with a specific dial code
     */
    fun getByDialCode(dialCode: String): List<Country> =
        dialCodeMap[dialCode] ?: emptyList()

    /**
     * Parses a full international phone number (e.g., "+8801712345678")
     * into a Pair of the matching Country and the remaining national number.
     */
    fun parsePhoneNumber(fullNumber: String): Pair<Country?, String> {
        // 1. Clean the input: keep only digits and the leading plus
        val cleaned = if (fullNumber.startsWith("+")) {
            "+" + fullNumber.filter { it.isDigit() }
        } else {
            fullNumber.filter { it.isDigit() }
        }

        if (cleaned.isEmpty()) return Pair(null, "")

        // 2. Find matching countries by dial code.
        // We sort by dialCode length descending to match "+1-268" before "+1"
        val sortedCountries = countries.sortedByDescending { it.dialCode.length }

        for (country in sortedCountries) {

            // Inside parsePhoneNumber loop
            val dialDigitsOnly = country.dialCode.filter { it.isDigit() }
            val inputDigitsOnly = cleaned.removePrefix("+")

            if (inputDigitsOnly.startsWith(dialDigitsOnly)) {
                val nationalNumber = inputDigitsOnly.removePrefix(dialDigitsOnly)
                return Pair(country, nationalNumber)
            }
        }

        return Pair(null, cleaned)
    }


}