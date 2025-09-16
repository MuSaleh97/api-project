package com.spring_api.back_end.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdhanResponse {
    @JsonProperty("Fajr")
    private String fajr;
    @JsonProperty("Sunrise")
    private String sunrise;
    @JsonProperty("Dhuhr")
    private String dhuhr;
    @JsonProperty("Asr")
    private String asr;
    @JsonProperty("Maghrib")
    private String maghrib;
    @JsonProperty("Isha")
    private String isha;
    @JsonProperty("gregorianCalendar")
    private String gregorianCalendar;
    @JsonProperty("hijriCalendar")
    private String hijriCalendar;
    @JsonProperty("theNextPrayer")
    private String theNextPrayer;
    @JsonProperty("TimeRemainingForTheNextPrayer")
    private String TimeRemainingForTheNextPrayer;
}
