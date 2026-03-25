package com.backend.pishop.mapper;

import org.springframework.stereotype.Component;

import com.backend.pishop.entity.Province;
import com.backend.pishop.response.ProvinceResponse;

@Component
public class ProvinceMapper {

    public ProvinceResponse toDTO(Province p) {
        return new ProvinceResponse(
                p.getCode(),
                p.getCodeName(),
                p.getFullName(),
                p.getFullNameEn(),
                p.getName(),
                p.getNameEn()
        );
    }
}