package com.mvalho.project.statisticsapi.controller;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionRestControllerTest extends RestControllerConfigTest {
    @Test
    public void saveTransactionShouldReceiveAPostMessageWhenTheServiceIsCalledThroughTransactionURL() throws Exception {
        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().is2xxSuccessful());
    }
}
