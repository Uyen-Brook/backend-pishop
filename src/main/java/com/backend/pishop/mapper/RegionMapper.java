package com.backend.pishop.mapper;

import org.springframework.stereotype.Component;

import com.backend.pishop.entity.AdministrativeRegion;
import com.backend.pishop.response.RegionResponse;

@Component
public class RegionMapper {

    public RegionResponse toDTO(AdministrativeRegion r) {
        return new RegionResponse(
                r.getId(),
                r.getName(),
                r.getNameEn(),
                r.getCodeName(),
                r.getCodeNameEn()
        );
    }
}