package com.mvalho.project.statisticsapi.util;

import com.mvalho.project.statisticsapi.dto.StatisticDTO;

public class StatisticDtoBuilder {
    private static StatisticDTO statisticDTO;

    public static StatisticDTOPropertiesBuilder create() {
        statisticDTO = new StatisticDTO();
        return new StatisticDTOPropertiesBuilder();
    }

    public static class StatisticDTOPropertiesBuilder {
        public StatisticDTOPropertiesBuilder withSum(double sum) {
            statisticDTO.setSum(Math.round(sum * 100.0) / 100.0);
            return this;
        }

        public StatisticDTOPropertiesBuilder withAverage(double average) {
            statisticDTO.setAvg(Math.round(average * 10000.0) / 10000.0);
            return this;
        }

        public StatisticDTOPropertiesBuilder withMax(double max) {
            statisticDTO.setMax(max);
            return this;
        }

        public StatisticDTOPropertiesBuilder withMin(double min) {
            statisticDTO.setMin(min);
            return this;
        }

        public StatisticDTOPropertiesBuilder withCount(long count) {
            statisticDTO.setCount(count);
            return this;
        }

        public StatisticDTO build() {
            return statisticDTO;
        }
    }
}
