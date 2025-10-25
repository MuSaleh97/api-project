package com.spring_api.back_end.util;

import java.util.regex.Pattern;

/**
 * Utility class for phone number validation and formatting
 * Supports international phone numbers from all Arab countries and beyond
 */
public class PhoneUtil {

    // Comprehensive international phone number pattern
    private static final Pattern INTERNATIONAL_PATTERN =
        Pattern.compile("^\\+[1-9]\\d{1,14}$");

    // Pattern for local phone numbers (to be formatted)
    private static final Pattern LOCAL_PATTERN =
        Pattern.compile("^[0-9]{7,15}$");

    /**
     * Validate if phone number is in correct format
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        String cleaned = cleanPhoneNumber(phoneNumber);

        // Check if it's already in international format
        if (cleaned.startsWith("+")) {
            return INTERNATIONAL_PATTERN.matcher(cleaned).matches();
        }

        // Check if it's a valid local number that can be formatted
        return LOCAL_PATTERN.matcher(cleaned).matches();
    }

    /**
     * Clean phone number by removing spaces, brackets, and hyphens
     */
    public static String cleanPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        return phoneNumber.trim().replaceAll("[\\s()\\-]", "");
    }

    /**
     * Format phone number to international format based on Arab countries
     */
    public static String formatToInternational(String phoneNumber, String countryCode) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }

        String cleaned = cleanPhoneNumber(phoneNumber);

        // Already international
        if (cleaned.startsWith("+")) {
            return cleaned;
        }

        // Handle 00 prefix (international calling prefix)
        if (cleaned.startsWith("00")) {
            return "+" + cleaned.substring(2);
        }

        // If no country code provided, try to auto-detect
        if (countryCode == null || countryCode.trim().isEmpty()) {
            countryCode = detectCountryCode(cleaned);
        }

        // Remove leading zero (common in many countries)
        if (cleaned.startsWith("0") && !countryCode.equals("1")) { // USA/Canada keep leading 1
            cleaned = cleaned.substring(1);
        }

        return "+" + countryCode + cleaned;
    }

    /**
     * Auto-detect country code based on phone number patterns
     * Supports all Arab countries and major international formats
     */
    private static String detectCountryCode(String phoneNumber) {
        // Arab Countries

        // Jordan - 9 digits starting with 7
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("7")) {
            return "962";
        }

        // Saudi Arabia - 9 digits starting with 5
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("5") &&
            (phoneNumber.startsWith("50") || phoneNumber.startsWith("53") ||
             phoneNumber.startsWith("54") || phoneNumber.startsWith("55") ||
             phoneNumber.startsWith("56") || phoneNumber.startsWith("58"))) {
            return "966";
        }

        // UAE - 9 digits starting with 5
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("5") &&
            (phoneNumber.startsWith("50") || phoneNumber.startsWith("52") ||
             phoneNumber.startsWith("54") || phoneNumber.startsWith("55") ||
             phoneNumber.startsWith("56") || phoneNumber.startsWith("58"))) {
            return "971";
        }

        // Egypt - 10 digits starting with 1
        if (phoneNumber.length() == 10 && phoneNumber.startsWith("1")) {
            return "20";
        }

        // Kuwait - 8 digits
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("5") || phoneNumber.startsWith("6") || phoneNumber.startsWith("9"))) {
            return "965";
        }

        // Qatar - 8 digits
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("3") || phoneNumber.startsWith("5") || phoneNumber.startsWith("6") || phoneNumber.startsWith("7"))) {
            return "974";
        }

        // Bahrain - 8 digits
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("3") || phoneNumber.startsWith("6"))) {
            return "973";
        }

        // Oman - 8 digits
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("7") || phoneNumber.startsWith("9"))) {
            return "968";
        }

        // Lebanon - 8 digits starting with 3, 7, or 8
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("3") || phoneNumber.startsWith("7") || phoneNumber.startsWith("8"))) {
            return "961";
        }

        // Syria - 9 digits
        if (phoneNumber.length() == 9 &&
            (phoneNumber.startsWith("9") || phoneNumber.startsWith("8"))) {
            return "963";
        }

        // Iraq - 10 digits starting with 7
        if (phoneNumber.length() == 10 && phoneNumber.startsWith("7")) {
            return "964";
        }

        // Palestine - 9 digits starting with 5
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("5")) {
            return "970";
        }

        // Yemen - 9 digits starting with 7
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("7")) {
            return "967";
        }

        // Algeria - 9 digits starting with 5, 6, or 7
        if (phoneNumber.length() == 9 &&
            (phoneNumber.startsWith("5") || phoneNumber.startsWith("6") || phoneNumber.startsWith("7"))) {
            return "213";
        }

        // Morocco - 9 digits starting with 6 or 7
        if (phoneNumber.length() == 9 &&
            (phoneNumber.startsWith("6") || phoneNumber.startsWith("7"))) {
            return "212";
        }

        // Tunisia - 8 digits starting with 2, 4, 5, or 9
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("2") || phoneNumber.startsWith("4") ||
             phoneNumber.startsWith("5") || phoneNumber.startsWith("9"))) {
            return "216";
        }

        // Libya - 9 digits starting with 9
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("9")) {
            return "218";
        }

        // Sudan - 9 digits starting with 9
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("9")) {
            return "249";
        }

        // Somalia - 8 digits starting with 6
        if (phoneNumber.length() == 8 && phoneNumber.startsWith("6")) {
            return "252";
        }

        // Mauritania - 8 digits
        if (phoneNumber.length() == 8 &&
            (phoneNumber.startsWith("2") || phoneNumber.startsWith("3") || phoneNumber.startsWith("4"))) {
            return "222";
        }

        // Djibouti - 8 digits
        if (phoneNumber.length() == 8 && phoneNumber.startsWith("7")) {
            return "253";
        }

        // Comoros - 7 digits
        if (phoneNumber.length() == 7 && phoneNumber.startsWith("3")) {
            return "269";
        }

        // Common international patterns

        // USA/Canada - 10 digits or 11 digits starting with 1
        if ((phoneNumber.length() == 10 && phoneNumber.matches("^[2-9]\\d{9}$")) ||
            (phoneNumber.length() == 11 && phoneNumber.startsWith("1"))) {
            return "1";
        }

        // UK - 10 digits starting with 7
        if (phoneNumber.length() == 10 && phoneNumber.startsWith("7")) {
            return "44";
        }

        // India - 10 digits starting with 6, 7, 8, or 9
        if (phoneNumber.length() == 10 &&
            (phoneNumber.startsWith("6") || phoneNumber.startsWith("7") ||
             phoneNumber.startsWith("8") || phoneNumber.startsWith("9"))) {
            return "91";
        }

        // If no pattern matches, return empty string
        // This will require user to provide full international number
        return "";
    }

    /**
     * Get country name from country code
     */
    public static String getCountryName(String countryCode) {
        if (countryCode == null) return "Unknown";

        switch (countryCode) {
            case "962": return "Jordan";
            case "966": return "Saudi Arabia";
            case "971": return "UAE";
            case "20": return "Egypt";
            case "965": return "Kuwait";
            case "974": return "Qatar";
            case "973": return "Bahrain";
            case "968": return "Oman";
            case "961": return "Lebanon";
            case "963": return "Syria";
            case "964": return "Iraq";
            case "970": return "Palestine";
            case "967": return "Yemen";
            case "213": return "Algeria";
            case "212": return "Morocco";
            case "216": return "Tunisia";
            case "218": return "Libya";
            case "249": return "Sudan";
            case "252": return "Somalia";
            case "222": return "Mauritania";
            case "253": return "Djibouti";
            case "269": return "Comoros";
            case "1": return "USA/Canada";
            case "44": return "UK";
            case "91": return "India";
            default: return "Unknown";
        }
    }
}
