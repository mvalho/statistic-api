package com.mvalho.project.statisticsapi.controller;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionRestControllerTest extends RestControllerConfigTest {

    private final String jsonRequest = "{\"amount\":12.3,\"timestamp\":1478192204000}";
    private String urlTemplate = "/transactions";

    @Test
    public void saveTransactionShouldReceiveAPostMessageWhenTheServiceIsCalledThroughTransactionURL() throws Exception {
        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonRequest).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void saveTransactionShouldAcceptATransactionAsMethodParameter() throws Exception {
        this.mockMvc.perform(
                post(urlTemplate).contentType(this.contentType).content(jsonRequest).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().is2xxSuccessful());
    }


}
