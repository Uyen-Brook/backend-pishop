package com.backend.pishop.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionResponse {

    private Integer id;
    private String name;
    private String nameEn;
    private String codeName;
    private String codeNameEn;
}