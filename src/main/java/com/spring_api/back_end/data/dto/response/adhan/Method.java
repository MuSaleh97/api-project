package com.spring_api.back_end.data.dto.response.adhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Method {
    private int id;
    private String name;
    private Params params;
    private Location location;
}
