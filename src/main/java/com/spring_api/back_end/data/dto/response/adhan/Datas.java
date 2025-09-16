package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Datas {
    private Timings timings;
    private DateInfo date;
    private Meta meta;
}
