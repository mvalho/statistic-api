package com.mvalho.project.statisticsapi.controller;

import com.mvalho.project.statisticsapi.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionRestControllerTest extends RestControllerConfigTest {

    private final String jsonRequest = "{\"amount\":12.3,\"timestamp\":1478192204000}";
    private final String amountExpression = "$.amount";
    private final String timestampExpression = "$.timestamp";
    private final double amountValue = 12.3;
    private final int timestampValue = 1478192204;
    private String urlTemplate = "/transactions";

    @Autowired
    private TransactionService transactionService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new TransactionRestController(this.transactionService))
                .build();
    }

    @Test
    public void saveTransactionShouldReceiveAPostMessageWhenTheServiceIsCalledThroughTransactionURL() throws Exception {
        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonRequest).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void saveTransactionShouldAcceptATransactionAsMethodParameter() throws Exception {
        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonRequest).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void saveTransactionShouldReturnATransactionWhenATransactionIsPassedAsParameter() throws Exception {
        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonRequest).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(amountExpression, is(amountValue)))
                .andExpect(jsonPath(timestampExpression, is(timestampValue)));
    }

    @Test
    public void saveTransactionShouldReturnCode204WhenATransactionTimestampIsOutsideOfLast60Seconds() throws Exception {
        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonRequest).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isNoContent())
                .andExpect(jsonPath(amountExpression, is(amountValue)))
                .andExpect(jsonPath(timestampExpression, is(timestampValue)));
    }

    @Test
    public void saveTransactionShouldReturnCode201WhenATransactionTimestampIsWithinOfLast60Seconds() throws Exception {
        Instant now = Instant.now();
        long timestampWithin60Seconds = now.toEpochMilli();
        int timestampExpected = (int) now.getEpochSecond();
        String jsonContent = "{\"amount\":12.3,\"timestamp\":" + timestampWithin60Seconds + "}";

        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonContent).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath(amountExpression, is(amountValue)))
                .andExpect(jsonPath(timestampExpression, is(timestampExpected)));
    }
}
