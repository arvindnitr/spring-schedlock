package com.arvind.scheduler.springschedlock.config;


import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
@EnableSchedulerLock(defaultLockAtMostFor = "PT2H")
@EnableJpaRepositories
@EnableAsync
@Slf4j
public class SchedulerAppConfig {

    @Autowired
    private SchedulingConfigProvider configProvider;

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .withTimeZone(TimeZone.getTimeZone("UTC"))
                        .build()
        );
    }

    @Bean(name="processExecutor")
    public TaskExecutor workExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("Async-");
        threadPoolTaskExecutor.setCorePoolSize(configProvider.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(configProvider.getMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(configProvider.getQueueCapacity());
        threadPoolTaskExecutor.afterPropertiesSet();
        log.info("ThreadPoolTaskExecutor set");
        return threadPoolTaskExecutor;
    }

}
