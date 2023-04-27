package com.neoflex.calculate.service;

public interface CalculatorService {

     double calculateVacationPay(double salary, int vacationDays);
     double calculateVacationPayWithHoliday(double salary, String startDate, String endDate);
     int paidVacationDays(String startDate, String endDate);





}
