package com.mvalho.project.statisticsapi.controller;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionRestControllerTest extends RestControllerConfigTest {

    private final String jsonRequest = "{\"amount\":12.3,\"timestamp\":1478192204000}";
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
                .andExpect(jsonPath("$.amount", is(12.3)))
                .andExpect(jsonPath("$.timestamp", is(1478192204000L)));
    }
}
