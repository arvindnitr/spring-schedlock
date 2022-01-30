package com.arvind.scheduler.springschedlock.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.yml")
public class SchedulingConfigProvider {

    @Value("${maxPoolSize:3}")
    private int maxPoolSize;

    @Value("${corePoolSize:3}")
    private int corePoolSize;

    @Value("${queueCapacity:600}")
    private int queueCapacity;

}
