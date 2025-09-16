package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hijri {
    private String date;
    private String format;
    private String day;
    private Weekday weekday;
    private Month month;
    private String year;
    private Designation designation;
    private List<String> holidays;
    private List<String> adjustedHolidays;
    private String method;
}
