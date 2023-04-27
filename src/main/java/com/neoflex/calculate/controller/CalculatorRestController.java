package com.neoflex.calculate.controller;

import com.neoflex.calculate.model.CalculatorResult;
import com.neoflex.calculate.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorRestController {

    @Autowired
    private CalculatorService calculatorService;


    @PostMapping("/rest/calculate")
    public CalculatorResult form(@RequestParam double salary, @RequestParam int vacationDays,
                                 @RequestParam String startDate, @RequestParam String endDate) {

        double vacationPay = calculatorService.calculateVacationPay(salary, vacationDays);
        double vacationPayHoliday = calculatorService.calculateVacationPayWithHoliday(salary, startDate, endDate);

        CalculatorResult calculatorResult = new CalculatorResult();

        calculatorResult.setVacationPay(vacationPay);
        calculatorResult.setVacationPayHoliday(vacationPayHoliday);

        return calculatorResult;
    }

}
