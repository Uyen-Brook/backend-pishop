package com.backend.pishop.mapper;

import org.springframework.stereotype.Component;

import com.backend.pishop.entity.Ward;
import com.backend.pishop.response.WardResponse;

@Component
public class WardMapper {

    public WardResponse toDTO(Ward w) {
        return new WardResponse(
                w.getId(),
                w.getCodeName(),
                w.getFullName(),
                w.getFullNameEn(),
                w.getName(),
                w.getNameEn()
        );
    }
}