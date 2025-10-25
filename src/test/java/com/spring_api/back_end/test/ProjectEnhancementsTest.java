package com.spring_api.back_end.test;

import com.spring_api.back_end.data.enums.Dictionary;
import com.spring_api.back_end.data.enums.Zone;
import com.spring_api.back_end.data.enums.ErrorMsg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Comprehensive test to verify the enhancements made to the API project
 */
public class ProjectEnhancementsTest {

    @Test
    public void testDictionaryEnumArabCountries() {
        // Test that major Arab countries are present
        Assertions.assertNotNull(Dictionary.resolveToEnglish("Jordan"));
        Assertions.assertNotNull(Dictionary.resolveToEnglish("الأردن")); // Jordan in Arabic

        Assertions.assertNotNull(Dictionary.resolveToEnglish("Saudi Arabia"));
        Assertions.assertNotNull(Dictionary.resolveToEnglish("المملكة العربية السعودية")); // Saudi Arabia in Arabic

        Assertions.assertNotNull(Dictionary.resolveToEnglish("Egypt"));
        Assertions.assertNotNull(Dictionary.resolveToEnglish("مصر")); // Egypt in Arabic

        // Test major cities
        Assertions.assertEquals("Amman", Dictionary.resolveToEnglish("عمان"));
        Assertions.assertEquals("Cairo", Dictionary.resolveToEnglish("القاهرة"));
        Assertions.assertEquals("Riyadh", Dictionary.resolveToEnglish("الرياض"));
        Assertions.assertEquals("Dubai", Dictionary.resolveToEnglish("دبي"));
    }

    @Test
    public void testZoneEnumArabCountries() {
        // Test that all Arab countries have correct time zones
        Assertions.assertEquals("Asia/Amman", Zone.getZoneIdByRegion("Jordan"));
        Assertions.assertEquals("Asia/Riyadh", Zone.getZoneIdByRegion("Saudi Arabia"));
        Assertions.assertEquals("Asia/Dubai", Zone.getZoneIdByRegion("United Arab Emirates"));
        Assertions.assertEquals("Africa/Cairo", Zone.getZoneIdByRegion("Egypt"));
        Assertions.assertEquals("Asia/Baghdad", Zone.getZoneIdByRegion("Iraq"));
        Assertions.assertEquals("Asia/Damascus", Zone.getZoneIdByRegion("Syria"));
        Assertions.assertEquals("Asia/Beirut", Zone.getZoneIdByRegion("Lebanon"));
        Assertions.assertEquals("Asia/Kuwait", Zone.getZoneIdByRegion("Kuwait"));
        Assertions.assertEquals("Asia/Qatar", Zone.getZoneIdByRegion("Qatar"));
        Assertions.assertEquals("Asia/Bahrain", Zone.getZoneIdByRegion("Bahrain"));
        Assertions.assertEquals("Asia/Muscat", Zone.getZoneIdByRegion("Oman"));
        Assertions.assertEquals("Asia/Aden", Zone.getZoneIdByRegion("Yemen"));

        // North African countries
        Assertions.assertEquals("Africa/Algiers", Zone.getZoneIdByRegion("Algeria"));
        Assertions.assertEquals("Africa/Casablanca", Zone.getZoneIdByRegion("Morocco"));
        Assertions.assertEquals("Africa/Tunis", Zone.getZoneIdByRegion("Tunisia"));
        Assertions.assertEquals("Africa/Tripoli", Zone.getZoneIdByRegion("Libya"));
        Assertions.assertEquals("Africa/Khartoum", Zone.getZoneIdByRegion("Sudan"));

        // Other Arab countries
        Assertions.assertEquals("Africa/Mogadishu", Zone.getZoneIdByRegion("Somalia"));
        Assertions.assertEquals("Africa/Nouakchott", Zone.getZoneIdByRegion("Mauritania"));
        Assertions.assertEquals("Africa/Djibouti", Zone.getZoneIdByRegion("Djibouti"));
        Assertions.assertEquals("Indian/Comoro", Zone.getZoneIdByRegion("Comoros"));
    }

    @Test
    public void testErrorMessagesExist() {
        // Test that all new error messages are properly defined
        Assertions.assertNotNull(ErrorMsg.INVALID_LOCATION.getEnDescription());
        Assertions.assertNotNull(ErrorMsg.INVALID_LOCATION.getArDescription());

        Assertions.assertNotNull(ErrorMsg.INVALID_ZONE_REGION.getEnDescription());
        Assertions.assertNotNull(ErrorMsg.INVALID_ZONE_REGION.getArDescription());

        Assertions.assertNotNull(ErrorMsg.EXTERNAL_API_ERROR.getEnDescription());
        Assertions.assertNotNull(ErrorMsg.EXTERNAL_API_ERROR.getArDescription());

        Assertions.assertNotNull(ErrorMsg.PRAYER_TIME_CALCULATION_ERROR.getEnDescription());
        Assertions.assertNotNull(ErrorMsg.PRAYER_TIME_CALCULATION_ERROR.getArDescription());

        // Verify bilingual support
        Assertions.assertFalse(ErrorMsg.INVALID_LOCATION.getEnDescription().isEmpty());
        Assertions.assertFalse(ErrorMsg.INVALID_LOCATION.getArDescription().isEmpty());
    }

    @Test
    public void testInvalidLocationHandling() {
        try {
            Dictionary.resolveToEnglish("InvalidLocation");
            Assertions.fail("Should have thrown InvalidLocationException");
        } catch (com.spring_api.back_end.business.exception.InvalidLocationException e) {
            Assertions.assertTrue(e.getMessage().contains("InvalidLocation"));
        }
    }

    @Test
    public void testInvalidZoneHandling() {
        try {
            Zone.getZoneIdByRegion("InvalidCountry");
            Assertions.fail("Should have thrown InvalidZoneRegionException");
        } catch (com.spring_api.back_end.business.exception.InvalidZoneRegionException e) {
            Assertions.assertTrue(e.getMessage().contains("InvalidCountry"));
        }
    }

    @Test
    public void testNullInputHandling() {
        try {
            Dictionary.resolveToEnglish(null);
            Assertions.fail("Should have thrown InvalidRequestDataException");
        } catch (com.spring_api.back_end.business.exception.InvalidRequestDataException e) {
            Assertions.assertTrue(e.getMessage().contains("null"));
        }

        try {
            Zone.getZoneIdByRegion(null);
            Assertions.fail("Should have thrown InvalidRequestDataException");
        } catch (com.spring_api.back_end.business.exception.InvalidRequestDataException e) {
            Assertions.assertTrue(e.getMessage().contains("null"));
        }
    }
}
