package com.mvalho.project.statisticsapi.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionRestControllerTest extends RestControllerConfigTest {

    private final String jsonRequest = "{\"amount\":12.3,\"timestamp\":1478192204000}";
    private final String amountExpression = "$.amount";
    private final String timestampExpression = "$.timestamp";
    private final double amountValue = 12.3;
    private final long timestampValue = 1478192204000L;
    private String urlTemplate = "/transactions";

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
                .andExpect(MockMvcResultMatchers.jsonPath(amountExpression, is(amountValue)))
                .andExpect(MockMvcResultMatchers.jsonPath(timestampExpression, is(timestampValue)));
    }
}
