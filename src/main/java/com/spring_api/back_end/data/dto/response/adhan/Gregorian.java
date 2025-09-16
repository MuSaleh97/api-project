package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gregorian {
    private String date;
    private String format;
    private String day;
    private Weekday weekday;
    private GregorianMonth month;
    private String year;
    private Designation designation;
    private boolean lunarSighting;
}
