package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offset {
    private int Imsak;
    private int Fajr;
    private int Sunrise;
    private int Dhuhr;
    private int Asr;
    private int Maghrib;
    private int Sunset;
    private int Isha;
    private int Midnight;
}
