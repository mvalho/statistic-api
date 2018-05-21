package com.mvalho.project.statisticsapi.controller;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.StatisticService;
import com.mvalho.project.statisticsapi.service.TransactionService;
import com.mvalho.project.statisticsapi.util.TransactionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticsRestControllerTest extends RestControllerConfigTest {

    private final String urlTemplate = "/statistics";

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private TransactionService transactionService;

    @Before
    public void setUp() {
        transactionDAO.resetTransactionList();

        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new StatisticRestController(this.statisticService),
                new TransactionRestController(this.transactionService))
                .build();

        LocalDateTime now = LocalDateTime.now(ZoneId.of("+0"));
        Transaction transaction1 = TransactionBuilder.create().withAmount(new BigDecimal(50.50)).withCreatedDate(now.minusSeconds(10)).build();
        Transaction transaction2 = TransactionBuilder.create().withAmount(new BigDecimal(27.65)).withCreatedDate(now).build();
        Transaction transaction3 = TransactionBuilder.create().withAmount(new BigDecimal(12.0)).withCreatedDate(now.minusSeconds(64)).build();
        Transaction transaction4 = TransactionBuilder.create().withAmount(new BigDecimal(56.78)).withCreatedDate(now.minusSeconds(70)).build();
        Transaction transaction5 = TransactionBuilder.create().withAmount(new BigDecimal(45.6)).withCreatedDate(now.minusSeconds(80)).build();
        Transaction transaction6 = TransactionBuilder.create().withAmount(new BigDecimal(98.31)).withCreatedDate(now.minusSeconds(35)).build();
        Transaction transaction7 = TransactionBuilder.create().withAmount(new BigDecimal(157.25)).withCreatedDate(now.minusSeconds(20)).build();
        Transaction transaction8 = TransactionBuilder.create().withAmount(new BigDecimal(98.7)).withCreatedDate(now.minusSeconds(61)).build();

        this.transactionDAO.add(transaction1);
        this.transactionDAO.add(transaction2);
        this.transactionDAO.add(transaction3);
        this.transactionDAO.add(transaction4);
        this.transactionDAO.add(transaction5);
        this.transactionDAO.add(transaction6);
        this.transactionDAO.add(transaction7);
        this.transactionDAO.add(transaction8);
    }

    @Test
    public void getStatisticsShouldBeAccessedByGetMethodWhenItIsCalledFromStatisticURL() throws Exception {
        this.mockMvc.perform(
                get(urlTemplate).contentType(this.contentType).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());
    }

    @Test
    public void getStatisticShouldReturnTheStatisticsFromTheLas60Seconds() throws Exception {
        this.mockMvc.perform(
                get(urlTemplate).contentType(this.contentType).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(333.71)))
                .andExpect(jsonPath("$.avg", is(83.4275)))
                .andExpect(jsonPath("$.max", is(157.25)))
                .andExpect(jsonPath("$.min", is(27.65)))
                .andExpect(jsonPath("$.count", is(4)));
    }
}