package com.spring_api.back_end.test;

import com.spring_api.back_end.util.PhoneUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Test class for SMS OTP functionality
 */
public class SmsOtpTest {

    @Test
    public void testPhoneNumberValidation() {
        // Valid international numbers
        Assertions.assertTrue(PhoneUtil.isValidPhoneNumber("+962791234567"));
        Assertions.assertTrue(PhoneUtil.isValidPhoneNumber("+966501234567"));
        Assertions.assertTrue(PhoneUtil.isValidPhoneNumber("+971501234567"));
        Assertions.assertTrue(PhoneUtil.isValidPhoneNumber("+201234567890"));

        // Valid local numbers
        Assertions.assertTrue(PhoneUtil.isValidPhoneNumber("791234567"));
        Assertions.assertTrue(PhoneUtil.isValidPhoneNumber("0791234567"));

        // Invalid numbers
        Assertions.assertFalse(PhoneUtil.isValidPhoneNumber(""));
        Assertions.assertFalse(PhoneUtil.isValidPhoneNumber(null));
        Assertions.assertFalse(PhoneUtil.isValidPhoneNumber("123"));
        Assertions.assertFalse(PhoneUtil.isValidPhoneNumber("abcdefg"));
    }

    @Test
    public void testPhoneNumberFormatting() {
        // Jordan numbers
        Assertions.assertEquals("+962791234567", PhoneUtil.formatToInternational("791234567", "962"));
        Assertions.assertEquals("+962791234567", PhoneUtil.formatToInternational("0791234567", "962"));
        Assertions.assertEquals("+962791234567", PhoneUtil.formatToInternational("+962791234567", null));

        // Saudi Arabia numbers
        Assertions.assertEquals("+966501234567", PhoneUtil.formatToInternational("501234567", "966"));
        Assertions.assertEquals("+966501234567", PhoneUtil.formatToInternational("0501234567", "966"));

        // Auto-detection
        Assertions.assertEquals("+962791234567", PhoneUtil.formatToInternational("791234567", null));
        Assertions.assertEquals("+966501234567", PhoneUtil.formatToInternational("501234567", null));
    }

    @Test
    public void testPhoneNumberCleaning() {
        Assertions.assertEquals("791234567", PhoneUtil.cleanPhoneNumber("79 123 4567"));
        Assertions.assertEquals("791234567", PhoneUtil.cleanPhoneNumber("(79) 123-4567"));
        Assertions.assertEquals("+962791234567", PhoneUtil.cleanPhoneNumber("+962 79 123 4567"));
        Assertions.assertEquals("0791234567", PhoneUtil.cleanPhoneNumber("0 79 123 4567"));
    }

    @Test
    public void testCountryCodeDetection() {
        // Test that country detection works for major Arab countries
        String jordanNumber = PhoneUtil.formatToInternational("791234567", null);
        Assertions.assertTrue(jordanNumber.startsWith("+962"));

        String saudiNumber = PhoneUtil.formatToInternational("501234567", null);
        Assertions.assertTrue(saudiNumber.startsWith("+966"));

        String uaeNumber = PhoneUtil.formatToInternational("521234567", null);
        Assertions.assertTrue(uaeNumber.startsWith("+971"));

        String egyptNumber = PhoneUtil.formatToInternational("1234567890", null);
        Assertions.assertTrue(egyptNumber.startsWith("+20"));
    }

    @Test
    public void testCountryNames() {
        Assertions.assertEquals("Jordan", PhoneUtil.getCountryName("962"));
        Assertions.assertEquals("Saudi Arabia", PhoneUtil.getCountryName("966"));
        Assertions.assertEquals("UAE", PhoneUtil.getCountryName("971"));
        Assertions.assertEquals("Egypt", PhoneUtil.getCountryName("20"));
        Assertions.assertEquals("Kuwait", PhoneUtil.getCountryName("965"));
        Assertions.assertEquals("Qatar", PhoneUtil.getCountryName("974"));
        Assertions.assertEquals("Unknown", PhoneUtil.getCountryName("999"));
    }
}
