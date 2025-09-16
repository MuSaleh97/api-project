package com.spring_api.back_end.data.dto.response.adhan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timings {
    @JsonProperty("Fajr")
    private String fajr;
    @JsonProperty("Sunrise")
    private String sunrise;
    @JsonProperty("Dhuhr")
    private String dhuhr;
    @JsonProperty("Asr")
    private String asr;
    @JsonProperty("Sunset")
    private String sunset;
    @JsonProperty("Maghrib")
    private String maghrib;
    @JsonProperty("Isha")
    private String isha;
    @JsonProperty("Imsak")
    private String imsak;
    @JsonProperty("Midnight")
    private String midnight;
    @JsonProperty("Firstthird")
    private String firstthird;
    @JsonProperty("Lastthird")
    private String lastthird;
}
