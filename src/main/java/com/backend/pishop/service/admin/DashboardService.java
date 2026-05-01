package com.backend.pishop.service.admin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.pishop.repository.OrderRepository;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.AccountRepository;
import com.backend.pishop.response.DashboardResponse;
import com.backend.pishop.response.DashboardResponse.TimeSeriesPoint;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    // Overview for a given date range
    public DashboardResponse getOverview(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(LocalTime.MAX);

        long totalOrders = orderRepository.countByCreatedAtBetween(start, end);
        long totalProducts = productRepository.count();
        long totalAccounts = accountRepository.count();
        BigDecimal totalRevenue = orderRepository.sumRevenueBetween(start, end);
        BigDecimal estimatedProfit = orderRepository.sumEstimatedProfitBetween(start, end);

        DashboardResponse response = DashboardResponse.builder()
                .totalOrders(totalOrders)
                .totalProducts(totalProducts)
                .totalAccounts(totalAccounts)
                .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
                .estimatedProfit(estimatedProfit != null ? estimatedProfit : BigDecimal.ZERO)
                .build();

        return response;
    }

    // Revenue time series grouped by day between two dates
    public List<TimeSeriesPoint> revenueByDay(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(LocalTime.MAX);
        List<Object[]> rows = orderRepository.revenueGroupedByDay(start, end);
        List<TimeSeriesPoint> list = new ArrayList<>();
        for (Object[] r : rows) {
            String period = String.valueOf(r[0]);
            BigDecimal value = r[1] == null ? BigDecimal.ZERO : new BigDecimal(r[1].toString());
            TimeSeriesPoint p = new TimeSeriesPoint();
            p.setPeriod(period);
            p.setValue(value);
            list.add(p);
        }
        return list;
    }

    // Revenue by month in a year
    public List<TimeSeriesPoint> revenueByMonth(int year) {
        List<Object[]> rows = orderRepository.revenueGroupedByMonth(year);
        List<TimeSeriesPoint> list = new ArrayList<>();
        for (Object[] r : rows) {
            String period = String.valueOf(r[0]);
            BigDecimal value = r[1] == null ? BigDecimal.ZERO : new BigDecimal(r[1].toString());
            TimeSeriesPoint p = new TimeSeriesPoint();
            p.setPeriod(period);
            p.setValue(value);
            list.add(p);
        }
        return list;
    }

    public List<TimeSeriesPoint> revenueByQuarter(int year) {
        List<Object[]> rows = orderRepository.revenueGroupedByQuarter(year);
        List<TimeSeriesPoint> list = new ArrayList<>();
        for (Object[] r : rows) {
            String period = String.valueOf(r[0]);
            BigDecimal value = r[1] == null ? BigDecimal.ZERO : new BigDecimal(r[1].toString());
            TimeSeriesPoint p = new TimeSeriesPoint();
            p.setPeriod(period);
            p.setValue(value);
            list.add(p);
        }
        return list;
    }

    public List<TimeSeriesPoint> revenueByYearRange(int startYear, int endYear) {
        List<Object[]> rows = orderRepository.revenueGroupedByYearRange(startYear, endYear);
        List<TimeSeriesPoint> list = new ArrayList<>();
        for (Object[] r : rows) {
            String period = String.valueOf(r[0]);
            BigDecimal value = r[1] == null ? BigDecimal.ZERO : new BigDecimal(r[1].toString());
            TimeSeriesPoint p = new TimeSeriesPoint();
            p.setPeriod(period);
            p.setValue(value);
            list.add(p);
        }
        return list;
    }

    // Count products added since a date
    public long countProductsAddedSince(LocalDate fromDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        return productRepository.count((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createAt"), start));
    }

}
