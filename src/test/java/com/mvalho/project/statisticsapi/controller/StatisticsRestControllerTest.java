package com.mvalho.project.statisticsapi.controller;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.service.StatisticService;
import com.mvalho.project.statisticsapi.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    }

    @Test
    public void getStatisticsShouldBeAccessedByGetMethodWhenItIsCalledFromStatisticURL() throws Exception {
        this.mockMvc.perform(
                get(urlTemplate).contentType(this.contentType).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());
    }

    @Test
    public void getStatisticShouldReturnTheStatisticsFromTheLas60Seconds() throws Exception {
        createTransactions();

        this.mockMvc.perform(
                get(urlTemplate).contentType(this.contentType).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(333.71)))
                .andExpect(jsonPath("$.avg", is(83.4275)))
                .andExpect(jsonPath("$.max", is(157.25)))
                .andExpect(jsonPath("$.min", is(27.65)))
                .andExpect(jsonPath("$.count", is(4)));
    }

    private void createTransactions() throws Exception {
        Instant now = Instant.now();

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":50.50,\"timestamp\":" + now.minusSeconds(10).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(70).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(80).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":27.65,\"timestamp\":" + now.minusSeconds(58).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":98.31,\"timestamp\":" + now.minusSeconds(5).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(90).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":157.25,\"timestamp\":" + now.minusSeconds(35).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(90).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());
    }
}
