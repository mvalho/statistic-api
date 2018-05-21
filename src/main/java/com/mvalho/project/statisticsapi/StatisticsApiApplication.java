package com.mvalho.project.statisticsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mvalho.project.statisticsapi"})
public class StatisticsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApiApplication.class, args);
    }
}
