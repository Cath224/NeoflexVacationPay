package com.neoflex.calculate;

import com.neoflex.calculate.controller.CalculatorRestController;
import com.neoflex.calculate.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class CalculateVacationPayApplicationTests {

    @Autowired
    private CalculatorService calculatorService;


    private MockMvc mvc;


    @BeforeEach
    public void init(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.standaloneSetup(new CalculatorRestController()).build();
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testCalculateVacationPay() {
        double result = calculatorService.calculateVacationPay(200_000, 10);
        assertThat(result).isEqualTo(68260.0);

        result = calculatorService.calculateVacationPay(0, 10);
        assertThat(result).isEqualTo(0);
    }

    @Test()
    public void testCalculateVacationPayWithHoliday() {
        double result = calculatorService.calculateVacationPayWithHoliday(200_000,
                LocalDate.of(2023, Month.MARCH, 6).toString(),
                LocalDate.of(2023, Month.MARCH, 12).toString());
        assertThat(result).isEqualTo(27304.0);
    }

    @Test
    public void testRestController() throws Exception {
        mvc.perform(post("/rest/calculate")
                        .queryParam("salary", "200000")
                        .queryParam("vacationDays", "10")
                        .queryParam("startDate", LocalDate.of(2023, Month.MARCH, 6).toString())
                        .queryParam("endDate", LocalDate.of(2023, Month.MARCH, 12).toString()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vacationPay").value(68260.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vacationPayHoliday").value(27304.0));
    }

}
