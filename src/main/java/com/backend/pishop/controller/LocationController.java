package com.backend.pishop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.pishop.entity.AdministrativeRegion;
import com.backend.pishop.entity.Province;
import com.backend.pishop.entity.Ward;
import com.backend.pishop.response.ProvinceResponse;
import com.backend.pishop.response.RegionResponse;
import com.backend.pishop.response.WardResponse;
import com.backend.pishop.service.LocationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    // 1. get all region
    @GetMapping("/regions")
    public ResponseEntity<List<RegionResponse>> getAllRegions() {
        return ResponseEntity.ok(locationService.getAllRegions());
    }

    // 2. get province by id
    @GetMapping("/provinces/{id}")
    public ResponseEntity<ProvinceResponse> getProvinceById(@PathVariable String id) {
        return ResponseEntity.ok(locationService.getProvinceById(id));
    }

    // 3. get all province by regionId
    @GetMapping("/provinces/region/{regionId}")
    public ResponseEntity<List<ProvinceResponse>> getProvinceByRegion(@PathVariable Integer regionId) {
        return ResponseEntity.ok(locationService.getProvinceByRegionId(regionId));
    }

    // 4. get all province
    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceResponse>> getAllProvinces() {
        return ResponseEntity.ok(locationService.getAllProvinces());
    }

    // 5. get ward by code
    @GetMapping("/wards/{code}")
    public ResponseEntity<WardResponse> getWardByCode(@PathVariable String code) {
        return ResponseEntity.ok(locationService.getWardByCode(code));
    }

    // 6. get all ward by province code
    @GetMapping("/wards/provinces/{provinceCode}")
    public ResponseEntity<List<WardResponse>> getWardsByProvince(@PathVariable String provinceCode) {
        return ResponseEntity.ok(locationService.getWardByProvinceCode(provinceCode));
    }
}