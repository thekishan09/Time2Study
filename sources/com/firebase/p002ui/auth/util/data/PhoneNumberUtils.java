package com.firebase.p002ui.auth.util.data;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.core.view.InputDeviceCompat;
import com.firebase.p002ui.auth.data.model.CountryInfo;
import com.firebase.p002ui.auth.data.model.PhoneNumber;
import com.google.logging.type.LogSeverity;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.util.data.PhoneNumberUtils */
public final class PhoneNumberUtils {
    private static Map<String, Integer> COUNTRY_TO_ISO_CODES = null;
    private static final SparseArray<List<String>> COUNTRY_TO_REGION_CODES = createCountryCodeToRegionCodeMap();
    private static final CountryInfo DEFAULT_COUNTRY = new CountryInfo(DEFAULT_LOCALE, 1);
    private static final String DEFAULT_COUNTRY_CODE = String.valueOf(1);
    private static final int DEFAULT_COUNTRY_CODE_INT = 1;
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private static final int MAX_COUNTRIES = 248;
    private static final int MAX_COUNTRY_CODES = 215;
    private static final int MAX_LENGTH_COUNTRY_CODE = 3;

    public static String format(String phoneNumber, CountryInfo countryInfo) {
        if (phoneNumber.startsWith("+")) {
            return phoneNumber;
        }
        return "+" + String.valueOf(countryInfo.getCountryCode()) + phoneNumber.replaceAll("[^\\d.]", "");
    }

    public static String formatUsingCurrentCountry(String phoneNumber, Context context) {
        return format(phoneNumber, getCurrentCountryInfo(context));
    }

    public static CountryInfo getCurrentCountryInfo(Context context) {
        Locale locale = getSimBasedLocale(context);
        if (locale == null) {
            locale = getOSLocale();
        }
        if (locale == null) {
            return DEFAULT_COUNTRY;
        }
        Integer countryCode = getCountryCode(locale.getCountry());
        return countryCode == null ? DEFAULT_COUNTRY : new CountryInfo(locale, countryCode.intValue());
    }

    public static PhoneNumber getPhoneNumber(String providedPhoneNumber) {
        String countryCode = DEFAULT_COUNTRY_CODE;
        String countryIso = DEFAULT_LOCALE.getCountry();
        String phoneNumber = providedPhoneNumber;
        if (providedPhoneNumber.startsWith("+")) {
            countryCode = getCountryCodeForPhoneNumberOrDefault(providedPhoneNumber);
            countryIso = getCountryIsoForCountryCode(countryCode);
            phoneNumber = stripCountryCode(providedPhoneNumber, countryCode);
        }
        return new PhoneNumber(phoneNumber, countryIso, countryCode);
    }

    public static boolean isValid(String number) {
        return number.startsWith("+") && getCountryCodeForPhoneNumber(number) != null;
    }

    public static boolean isValidIso(String iso) {
        return getCountryCode(iso) != null;
    }

    public static PhoneNumber getPhoneNumber(String providedCountryIso, String providedNationalNumber) {
        int countryCode = getCountryCode(providedCountryIso);
        if (countryCode == null) {
            countryCode = 1;
            providedCountryIso = DEFAULT_COUNTRY_CODE;
        }
        return new PhoneNumber(stripPlusSign(providedNationalNumber), providedCountryIso, String.valueOf(countryCode));
    }

    public static Integer getCountryCode(String countryIso) {
        if (COUNTRY_TO_ISO_CODES == null) {
            initCountryCodeByIsoMap();
        }
        if (countryIso == null) {
            return null;
        }
        return COUNTRY_TO_ISO_CODES.get(countryIso.toUpperCase(Locale.getDefault()));
    }

    public static Map<String, Integer> getImmutableCountryIsoMap() {
        if (COUNTRY_TO_ISO_CODES == null) {
            initCountryCodeByIsoMap();
        }
        return COUNTRY_TO_ISO_CODES;
    }

    private static String getCountryIsoForCountryCode(String countryCode) {
        List<String> countries = COUNTRY_TO_REGION_CODES.get(Integer.parseInt(countryCode));
        if (countries != null) {
            return countries.get(0);
        }
        return DEFAULT_LOCALE.getCountry();
    }

    public static List<String> getCountryIsosFromCountryCode(String countryCode) {
        if (!isValid(countryCode)) {
            return null;
        }
        return COUNTRY_TO_REGION_CODES.get(Integer.parseInt(countryCode.substring(1)));
    }

    private static String getCountryCodeForPhoneNumber(String normalizedPhoneNumber) {
        String phoneWithoutPlusPrefix = normalizedPhoneNumber.replaceFirst("^\\+", "");
        int numberLength = phoneWithoutPlusPrefix.length();
        int i = 1;
        while (i <= 3 && i <= numberLength) {
            String potentialCountryCode = phoneWithoutPlusPrefix.substring(0, i);
            if (COUNTRY_TO_REGION_CODES.indexOfKey(Integer.valueOf(potentialCountryCode).intValue()) >= 0) {
                return potentialCountryCode;
            }
            i++;
        }
        return null;
    }

    private static String getCountryCodeForPhoneNumberOrDefault(String normalizedPhoneNumber) {
        String code = getCountryCodeForPhoneNumber(normalizedPhoneNumber);
        return code == null ? DEFAULT_COUNTRY_CODE : code;
    }

    private static String stripCountryCode(String phoneNumber, String countryCode) {
        return phoneNumber.replaceFirst("^\\+?" + countryCode, "");
    }

    private static String stripPlusSign(String phoneNumber) {
        return phoneNumber.replaceFirst("^\\+?", "");
    }

    private static Locale getSimBasedLocale(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        String countryIso = tm != null ? tm.getSimCountryIso() : null;
        if (TextUtils.isEmpty(countryIso)) {
            return null;
        }
        return new Locale("", countryIso);
    }

    private static Locale getOSLocale() {
        return Locale.getDefault();
    }

    private static SparseArray<List<String>> createCountryCodeToRegionCodeMap() {
        SparseArray<List<String>> map = new SparseArray<>(MAX_COUNTRY_CODES);
        map.put(1, Arrays.asList(new String[]{"US", "AG", "AI", "AS", "BB", "BM", "BS", "CA", "DM", "DO", "GD", "GU", "JM", "KN", "KY", "LC", "MP", "MS", "PR", "SX", "TC", "TT", "VC", "VG", "VI"}));
        map.put(7, Arrays.asList(new String[]{"RU", "KZ"}));
        map.put(20, Collections.singletonList("EG"));
        map.put(27, Collections.singletonList("ZA"));
        map.put(30, Collections.singletonList("GR"));
        map.put(31, Collections.singletonList("NL"));
        map.put(32, Collections.singletonList("BE"));
        map.put(33, Collections.singletonList("FR"));
        map.put(34, Collections.singletonList("ES"));
        map.put(36, Collections.singletonList("HU"));
        map.put(39, Collections.singletonList("IT"));
        map.put(40, Collections.singletonList("RO"));
        map.put(41, Collections.singletonList("CH"));
        map.put(43, Collections.singletonList("AT"));
        map.put(44, Arrays.asList(new String[]{"GB", "GG", "IM", "JE"}));
        map.put(45, Collections.singletonList("DK"));
        map.put(46, Collections.singletonList("SE"));
        map.put(47, Arrays.asList(new String[]{"NO", "SJ"}));
        map.put(48, Collections.singletonList("PL"));
        map.put(49, Collections.singletonList("DE"));
        map.put(51, Collections.singletonList("PE"));
        map.put(52, Collections.singletonList("MX"));
        map.put(53, Collections.singletonList("CU"));
        map.put(54, Collections.singletonList("AR"));
        map.put(55, Collections.singletonList("BR"));
        map.put(56, Collections.singletonList("CL"));
        map.put(57, Collections.singletonList("CO"));
        map.put(58, Collections.singletonList("VE"));
        map.put(60, Collections.singletonList("MY"));
        map.put(61, Arrays.asList(new String[]{"AU", "CC", "CX"}));
        map.put(62, Collections.singletonList("ID"));
        map.put(63, Collections.singletonList("PH"));
        map.put(64, Collections.singletonList("NZ"));
        map.put(65, Collections.singletonList("SG"));
        map.put(66, Collections.singletonList("TH"));
        map.put(81, Collections.singletonList("JP"));
        map.put(82, Collections.singletonList("KR"));
        map.put(84, Collections.singletonList("VN"));
        map.put(86, Collections.singletonList("CN"));
        map.put(90, Collections.singletonList("TR"));
        map.put(91, Collections.singletonList("IN"));
        map.put(92, Collections.singletonList("PK"));
        map.put(93, Collections.singletonList("AF"));
        map.put(94, Collections.singletonList("LK"));
        map.put(95, Collections.singletonList("MM"));
        map.put(98, Collections.singletonList("IR"));
        map.put(211, Collections.singletonList("SS"));
        map.put(212, Arrays.asList(new String[]{"MA", "EH"}));
        map.put(213, Collections.singletonList("DZ"));
        map.put(216, Collections.singletonList("TN"));
        map.put(218, Collections.singletonList("LY"));
        map.put(220, Collections.singletonList("GM"));
        map.put(221, Collections.singletonList("SN"));
        map.put(222, Collections.singletonList("MR"));
        map.put(223, Collections.singletonList("ML"));
        map.put(224, Collections.singletonList("GN"));
        map.put(225, Collections.singletonList("CI"));
        map.put(226, Collections.singletonList("BF"));
        map.put(227, Collections.singletonList("NE"));
        map.put(228, Collections.singletonList("TG"));
        map.put(229, Collections.singletonList("BJ"));
        map.put(230, Collections.singletonList("MU"));
        map.put(231, Collections.singletonList("LR"));
        map.put(232, Collections.singletonList("SL"));
        map.put(233, Collections.singletonList("GH"));
        map.put(234, Collections.singletonList("NG"));
        map.put(235, Collections.singletonList("TD"));
        map.put(236, Collections.singletonList("CF"));
        map.put(237, Collections.singletonList("CM"));
        map.put(238, Collections.singletonList("CV"));
        map.put(239, Collections.singletonList("ST"));
        map.put(240, Collections.singletonList("GQ"));
        map.put(241, Collections.singletonList("GA"));
        map.put(242, Collections.singletonList("CG"));
        map.put(243, Collections.singletonList("CD"));
        map.put(244, Collections.singletonList("AO"));
        map.put(245, Collections.singletonList("GW"));
        map.put(246, Collections.singletonList("IO"));
        map.put(247, Collections.singletonList("AC"));
        map.put(MAX_COUNTRIES, Collections.singletonList("SC"));
        map.put(249, Collections.singletonList("SD"));
        map.put(250, Collections.singletonList("RW"));
        map.put(251, Collections.singletonList("ET"));
        map.put(252, Collections.singletonList("SO"));
        map.put(253, Collections.singletonList("DJ"));
        map.put(254, Collections.singletonList("KE"));
        map.put(255, Collections.singletonList("TZ"));
        map.put(256, Collections.singletonList("UG"));
        map.put(InputDeviceCompat.SOURCE_KEYBOARD, Collections.singletonList("BI"));
        map.put(258, Collections.singletonList("MZ"));
        map.put(260, Collections.singletonList("ZM"));
        map.put(261, Collections.singletonList("MG"));
        map.put(262, Arrays.asList(new String[]{"RE", "YT"}));
        map.put(263, Collections.singletonList("ZW"));
        map.put(264, Collections.singletonList("NA"));
        map.put(265, Collections.singletonList("MW"));
        map.put(266, Collections.singletonList("LS"));
        map.put(267, Collections.singletonList("BW"));
        map.put(268, Collections.singletonList("SZ"));
        map.put(269, Collections.singletonList("KM"));
        map.put(290, Arrays.asList(new String[]{"SH", "TA"}));
        map.put(291, Collections.singletonList("ER"));
        map.put(297, Collections.singletonList("AW"));
        map.put(298, Collections.singletonList("FO"));
        map.put(299, Collections.singletonList("GL"));
        map.put(350, Collections.singletonList("GI"));
        map.put(351, Collections.singletonList("PT"));
        map.put(352, Collections.singletonList("LU"));
        map.put(353, Collections.singletonList("IE"));
        map.put(354, Collections.singletonList("IS"));
        map.put(355, Collections.singletonList("AL"));
        map.put(356, Collections.singletonList("MT"));
        map.put(357, Collections.singletonList("CY"));
        map.put(358, Arrays.asList(new String[]{"FI", "AX"}));
        map.put(359, Collections.singletonList("BG"));
        map.put(370, Collections.singletonList("LT"));
        map.put(371, Collections.singletonList("LV"));
        map.put(372, Collections.singletonList("EE"));
        map.put(373, Collections.singletonList("MD"));
        map.put(374, Collections.singletonList("AM"));
        map.put(375, Collections.singletonList("BY"));
        map.put(376, Collections.singletonList("AD"));
        map.put(377, Collections.singletonList("MC"));
        map.put(378, Collections.singletonList("SM"));
        map.put(379, Collections.singletonList("VA"));
        map.put(380, Collections.singletonList("UA"));
        map.put(381, Collections.singletonList("RS"));
        map.put(382, Collections.singletonList("ME"));
        map.put(385, Collections.singletonList("HR"));
        map.put(386, Collections.singletonList("SI"));
        map.put(387, Collections.singletonList("BA"));
        map.put(389, Collections.singletonList("MK"));
        map.put(420, Collections.singletonList("CZ"));
        map.put(421, Collections.singletonList("SK"));
        map.put(423, Collections.singletonList("LI"));
        map.put(LogSeverity.ERROR_VALUE, Collections.singletonList("FK"));
        map.put(501, Collections.singletonList("BZ"));
        map.put(502, Collections.singletonList("GT"));
        map.put(503, Collections.singletonList("SV"));
        map.put(504, Collections.singletonList("HN"));
        map.put(505, Collections.singletonList("NI"));
        map.put(506, Collections.singletonList("CR"));
        map.put(507, Collections.singletonList("PA"));
        map.put(508, Collections.singletonList("PM"));
        map.put(509, Collections.singletonList("HT"));
        map.put(590, Arrays.asList(new String[]{"GP", "BL", "MF"}));
        map.put(591, Collections.singletonList("BO"));
        map.put(592, Collections.singletonList("GY"));
        map.put(593, Collections.singletonList("EC"));
        map.put(594, Collections.singletonList("GF"));
        map.put(595, Collections.singletonList("PY"));
        map.put(596, Collections.singletonList("MQ"));
        map.put(597, Collections.singletonList("SR"));
        map.put(598, Collections.singletonList("UY"));
        map.put(599, Arrays.asList(new String[]{"CW", "BQ"}));
        map.put(670, Collections.singletonList("TL"));
        map.put(672, Collections.singletonList("NF"));
        map.put(673, Collections.singletonList("BN"));
        map.put(674, Collections.singletonList("NR"));
        map.put(675, Collections.singletonList("PG"));
        map.put(676, Collections.singletonList("TO"));
        map.put(677, Collections.singletonList("SB"));
        map.put(678, Collections.singletonList("VU"));
        map.put(679, Collections.singletonList("FJ"));
        map.put(680, Collections.singletonList("PW"));
        map.put(681, Collections.singletonList("WF"));
        map.put(682, Collections.singletonList("CK"));
        map.put(683, Collections.singletonList("NU"));
        map.put(685, Collections.singletonList("WS"));
        map.put(686, Collections.singletonList("KI"));
        map.put(687, Collections.singletonList("NC"));
        map.put(688, Collections.singletonList("TV"));
        map.put(689, Collections.singletonList("PF"));
        map.put(690, Collections.singletonList("TK"));
        map.put(691, Collections.singletonList("FM"));
        map.put(692, Collections.singletonList("MH"));
        map.put(LogSeverity.EMERGENCY_VALUE, Collections.singletonList("001"));
        map.put(808, Collections.singletonList("001"));
        map.put(850, Collections.singletonList("KP"));
        map.put(852, Collections.singletonList("HK"));
        map.put(853, Collections.singletonList("MO"));
        map.put(855, Collections.singletonList("KH"));
        map.put(856, Collections.singletonList("LA"));
        map.put(870, Collections.singletonList("001"));
        map.put(878, Collections.singletonList("001"));
        map.put(880, Collections.singletonList("BD"));
        map.put(881, Collections.singletonList("001"));
        map.put(882, Collections.singletonList("001"));
        map.put(883, Collections.singletonList("001"));
        map.put(886, Collections.singletonList("TW"));
        map.put(888, Collections.singletonList("001"));
        map.put(960, Collections.singletonList("MV"));
        map.put(961, Collections.singletonList("LB"));
        map.put(962, Collections.singletonList("JO"));
        map.put(963, Collections.singletonList("SY"));
        map.put(964, Collections.singletonList("IQ"));
        map.put(965, Collections.singletonList("KW"));
        map.put(966, Collections.singletonList("SA"));
        map.put(967, Collections.singletonList("YE"));
        map.put(968, Collections.singletonList("OM"));
        map.put(970, Collections.singletonList("PS"));
        map.put(971, Collections.singletonList("AE"));
        map.put(972, Collections.singletonList("IL"));
        map.put(973, Collections.singletonList("BH"));
        map.put(974, Collections.singletonList("QA"));
        map.put(975, Collections.singletonList("BT"));
        map.put(976, Collections.singletonList("MN"));
        map.put(977, Collections.singletonList("NP"));
        map.put(979, Collections.singletonList("001"));
        map.put(992, Collections.singletonList("TJ"));
        map.put(993, Collections.singletonList("TM"));
        map.put(994, Collections.singletonList("AZ"));
        map.put(995, Collections.singletonList("GE"));
        map.put(996, Collections.singletonList("KG"));
        map.put(998, Collections.singletonList("UZ"));
        return map;
    }

    private static void initCountryCodeByIsoMap() {
        Map<String, Integer> map = new HashMap<>(MAX_COUNTRIES);
        for (int i = 0; i < COUNTRY_TO_REGION_CODES.size(); i++) {
            int code = COUNTRY_TO_REGION_CODES.keyAt(i);
            for (String region : COUNTRY_TO_REGION_CODES.get(code)) {
                if (!region.equals("001")) {
                    if (!map.containsKey(region)) {
                        map.put(region, Integer.valueOf(code));
                    } else {
                        throw new IllegalStateException("Duplicate regions for country code: " + code);
                    }
                }
            }
        }
        map.remove("TA");
        map.put("HM", 672);
        map.put("GS", Integer.valueOf(LogSeverity.ERROR_VALUE));
        map.put("XK", 381);
        COUNTRY_TO_ISO_CODES = Collections.unmodifiableMap(map);
    }
}
