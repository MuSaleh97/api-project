package com.spring_api.back_end.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dictionary {
    AMMAN("Amman", "عمان"),
    JORDAN("Jordan", "الأردن");

    private final String enName;
    private final String arName;

    public static String resolveToEnglish(String inputName) {
        if (inputName == null) {
            return null;
        }
        for (Dictionary entry : Dictionary.values()) {
            if (entry.getArName().equalsIgnoreCase(inputName)) {
                return entry.getEnName();
            }
        }
        for (Dictionary entry : Dictionary.values()) {
            if (entry.getEnName().equalsIgnoreCase(inputName)) {
                return entry.getEnName();
            }
        }
        return null;
    }
}
