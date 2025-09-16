package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {
    private double latitude;
    private double longitude;
    private String timezone;
    private Method method;
    private String latitudeAdjustmentMethod;
    private String midnightMode;
    private String school;
    private Offset offset;
}
