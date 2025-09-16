package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrayerTimesResponse {
    private int code;
    private String status;
    private Datas data;
}
