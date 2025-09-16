package com.spring_api.back_end.util;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static Long generateCustomId() {
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%02d", now.getYear() % 100);
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());
        int randomNumber = ThreadLocalRandom.current().nextInt(1000000, 10000000);
        String randomPart = String.valueOf(randomNumber);
        String idString = year + month + day + hour + minute + randomPart;
        return Long.parseLong(idString);
    }

    public static Long generateOtpId() {
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%02d", now.getYear() % 100);
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 100000);
        String randomPart = String.valueOf(randomNumber);
        String idString = year + month + day + hour + minute + randomPart;
        return Long.parseLong(idString);
    }
}
