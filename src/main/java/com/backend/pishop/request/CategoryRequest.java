package com.backend.pishop.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    private String icon;
    private String note;
}