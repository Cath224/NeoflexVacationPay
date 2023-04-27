package com.neoflex.calculate.controller;


import com.neoflex.calculate.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CalculatorController {


    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/calculate")
    public String get(Model model) {
        return "index";
    }

    @PostMapping("/calculate")
    public String form(@RequestParam double salary, @RequestParam int vacationDays,
                       @RequestParam String startDate, @RequestParam String endDate, Model model) {

        double vacationPay = calculatorService.calculateVacationPay(salary, vacationDays);
        double vacationPayHoliday = calculatorService.calculateVacationPayWithHoliday(salary, startDate, endDate);

        model.addAttribute("vacationPay", vacationPay);
        model.addAttribute("vacationPayHoliday", vacationPayHoliday);

        return "index";
    }



}
