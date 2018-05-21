package com.mvalho.project.statisticsapi.controller;

import com.mvalho.project.statisticsapi.dto.StatisticDTO;
import com.mvalho.project.statisticsapi.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class StatisticRestController {
    private StatisticService statisticService;

    @Autowired
    public StatisticRestController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping(method = GET, value = "/statistics")
    public ResponseEntity<StatisticDTO> getStatistics() {
        StatisticDTO statistics = this.statisticService.getStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
