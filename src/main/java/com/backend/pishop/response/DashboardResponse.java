package com.backend.pishop.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private long totalOrders;
    private long totalProducts;
    private long totalAccounts;
    private BigDecimal totalRevenue;
    private BigDecimal estimatedProfit;

    // Time series for revenue and profit
    private List<TimeSeriesPoint> revenueSeries;
    private List<TimeSeriesPoint> profitSeries;

    // Counts
    private long productsAddedSince;

    @Data
    public static class TimeSeriesPoint {
        private String period;
        private BigDecimal value;
    }
}