package com.backend.pishop.enums;

public enum AccountRank {
    NEW("mới"),
    BRONZE("đồng"),
    SILVER("bạc"),
    GOLD("vàng"),
    DIAMOND("kim cương"),
    VIP("VIP");
    private String description;
    AccountRank(String description){ this.description = description; }
}