package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfo {
    private String readable;
    private String timestamp;
    private Hijri hijri;
    private Gregorian gregorian;
}
