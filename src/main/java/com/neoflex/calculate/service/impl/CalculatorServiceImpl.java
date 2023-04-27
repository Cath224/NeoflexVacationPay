package com.neoflex.calculate.service.impl;


import com.neoflex.calculate.service.CalculatorService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final Map<Integer, Set<Integer>> HOLIDAYS = Map.of(1, Set.of(1, 2, 3, 4, 5, 6, 7),
            2, Set.of(23),
            3, Set.of(8),
            5, Set.of(1, 9),
            6, Set.of(12),
            11, Set.of(4));

    @Override
    public double calculateVacationPay(double salary, int vacationDays) {
        if (salary > 0 && vacationDays > 0) {
            return Math.ceil((salary / 29.3) * vacationDays);
        } else {
            return 0.0;
        }
    }

    @Override
    public double calculateVacationPayWithHoliday(double salary, String startDate, String endDate) {
        int paidDays = paidVacationDays(startDate, endDate);
        return calculateVacationPay(salary, paidDays);
    }

    @Override
    public int paidVacationDays(String startDate, String endDate) {

        LocalDate parseStartDate;
        LocalDate parseEndDate;

        try {
            parseStartDate = LocalDate.parse(startDate);
            parseEndDate = LocalDate.parse(endDate);

        } catch (DateTimeParseException c) {
            System.out.println("Incorrect Date format");
            throw new RuntimeException("Incorrect Date format");
        }

        int paidDays = 0;
        while (!parseStartDate.isEqual(parseEndDate)) {
            DayOfWeek dayOfWeek = parseStartDate.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                parseStartDate = parseStartDate.plusDays(1);
                continue;
            } else {
                Set<Integer> dayOfMonth = HOLIDAYS.get(parseStartDate.getMonthValue());
                if (dayOfMonth != null && dayOfMonth.contains(parseStartDate.getDayOfMonth())) {
                    parseStartDate = parseStartDate.plusDays(1);
                    continue;
                }
            }
            paidDays++;
            parseStartDate = parseStartDate.plusDays(1);
        }
        return paidDays;
    }
}
