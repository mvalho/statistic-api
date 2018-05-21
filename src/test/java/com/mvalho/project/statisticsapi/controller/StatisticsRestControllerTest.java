package com.mvalho.project.statisticsapi.controller;

import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticsRestControllerTest extends RestControllerConfigTest {

    private final String urlTemplate = "/statistics";

    @Test
    public void getStatisticsShouldBeAccessedByGetMethodWhenItIsCalledFromStatisticURL() throws Exception {
        this.mockMvc.perform(
                get(urlTemplate).contentType(this.contentType).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());
    }

    @Test
    public void getStatisticShouldReturnTheStatisticsFromTheLas60Seconds() throws Exception {
        Instant now = Instant.now();

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":50.50,\"timestamp\":" + now.minusSeconds(10).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(70).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(61).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
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
                post("/transactions").contentType(this.contentType).content("{\"amount\":12.3,\"timestamp\":" + now.minusSeconds(64).toEpochMilli() + "}").accept(APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated());

        this.mockMvc.perform(
                get(urlTemplate).contentType(this.contentType).accept(APPLICATION_JSON_UTF8_VALUE)
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(333.71)))
                .andExpect(jsonPath("$.avg", is(83.4275)))
                .andExpect(jsonPath("$.max", is(157.25)))
                .andExpect(jsonPath("$.min", is(27.65)))
                .andExpect(jsonPath("$.count", is(4)));
    }
}
