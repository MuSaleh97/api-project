package com.spring_api.back_end.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NextPrayerInfo {
    public String prayerName;
    public Duration timeRemaining;
}
