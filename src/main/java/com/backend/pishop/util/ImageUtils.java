package com.backend.pishop.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ImageUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    // convert list → json
    public static String toJson(List<String> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException("Convert error");
        }
    }

    // json → list
    public static List<String> toList(String json) {
        try {
            return mapper.readValue(json, List.class);
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }

    // extract publicId từ cloudinary url
    public static String extractPublicId(String url) {
        String[] parts = url.split("/");
        String file = parts[parts.length - 1];
        return "pishop/products/" + file.substring(0, file.lastIndexOf("."));
    }
}