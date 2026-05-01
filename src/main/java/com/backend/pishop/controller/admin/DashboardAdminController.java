package com.backend.pishop.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.pishop.response.DashboardResponse;
import com.backend.pishop.response.DashboardResponse.TimeSeriesPoint;
import com.backend.pishop.service.admin.DashboardService;
import com.backend.pishop.config.APIResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    public APIResponse<DashboardResponse> overview(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        DashboardResponse resp = dashboardService.getOverview(from, to);
        // also set products added since
        resp.setProductsAddedSince(dashboardService.countProductsAddedSince(from));
        return APIResponse.<DashboardResponse>builder().result(resp).build();
    }

    @GetMapping("/revenue/day")
    public APIResponse<List<TimeSeriesPoint>> revenueByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return APIResponse.<List<TimeSeriesPoint>>builder().result(dashboardService.revenueByDay(from, to)).build();
    }

    @GetMapping("/revenue/month")
    public APIResponse<List<TimeSeriesPoint>> revenueByMonth(@RequestParam int year) {
        return APIResponse.<List<TimeSeriesPoint>>builder().result(dashboardService.revenueByMonth(year)).build();
    }

    @GetMapping("/revenue/quarter")
    public APIResponse<List<TimeSeriesPoint>> revenueByQuarter(@RequestParam int year) {
        return APIResponse.<List<TimeSeriesPoint>>builder().result(dashboardService.revenueByQuarter(year)).build();
    }

    @GetMapping("/revenue/year")
    public APIResponse<List<TimeSeriesPoint>> revenueByYearRange(@RequestParam int startYear, @RequestParam int endYear) {
        return APIResponse.<List<TimeSeriesPoint>>builder().result(dashboardService.revenueByYearRange(startYear, endYear)).build();
    }

}
