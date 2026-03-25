package com.backend.pishop.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponse {

    private String code;
    private String codeName;
    private String fullName;
    private String fullNameEn;
    private String name;
    private String nameEn;
}