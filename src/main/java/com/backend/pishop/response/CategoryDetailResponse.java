package com.backend.pishop.response;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDetailResponse extends CategoryResponse {
	private List<ProductSumaryResponse> products;
}
