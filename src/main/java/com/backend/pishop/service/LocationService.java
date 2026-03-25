package com.backend.pishop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.pishop.entity.AdministrativeRegion;
import com.backend.pishop.entity.Province;
import com.backend.pishop.entity.Ward;
import com.backend.pishop.mapper.ProvinceMapper;
import com.backend.pishop.mapper.RegionMapper;
import com.backend.pishop.mapper.WardMapper;
import com.backend.pishop.repository.AdministrativeRegionRepository;
import com.backend.pishop.repository.ProvinceRepository;
import com.backend.pishop.repository.WardRepository;
import com.backend.pishop.response.ProvinceResponse;
import com.backend.pishop.response.RegionResponse;
import com.backend.pishop.response.WardResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
	
	private final AdministrativeRegionRepository regionRepo;
	private final ProvinceRepository provinceRepo;
	private final WardRepository wardRepo;

	private final ProvinceMapper provinceMapper;
	private final WardMapper wardMapper;
	private final RegionMapper regionMapper;
	
	// 1. get all region
	public List<RegionResponse> getAllRegions() {
		return regionRepo.findAll().stream().map(regionMapper::toDTO).toList();
	}
	// 2. get province by id
	public ProvinceResponse getProvinceById(String id) {
		return provinceRepo.findById(id).map(provinceMapper::toDTO)
				.orElseThrow(() -> new RuntimeException("Province not found"));
	}
	// 3. get all province by regionId
	public List<ProvinceResponse> getProvinceByRegionId(Integer regionId) {
		return provinceRepo.findByRegion_Id(regionId).stream().map(provinceMapper::toDTO).toList();
	}
	// 4. get all province
	public List<ProvinceResponse> getAllProvinces() {
		return provinceRepo.findAll().stream().map(provinceMapper::toDTO).toList();
	}
	// 5. get ward by code
	public WardResponse getWardByCode(String code) {
		return wardRepo.findById(code).map(wardMapper::toDTO).orElseThrow(() -> new RuntimeException("Ward not found"));
	}
	// 6. get all ward by province code
	public List<WardResponse> getWardByProvinceCode(String provinceCode) {
		return wardRepo.findByProvince_Code(provinceCode).stream().map(wardMapper::toDTO).toList();
	}

}
