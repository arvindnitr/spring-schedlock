package com.arvind.scheduler.springschedlock.service;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ScheduledAsyncJobService {

    @Async("processExecutor")
    @Scheduled(fixedRateString = "PT10S")
    @SchedulerLock(name = "TaskScheduler_scheduledPrintTimeAsyncTask",
            lockAtLeastFor = "PT1M", lockAtMostFor= "PT5M")
    public void printTimeAsync() {
        log.info("Current time Async: " + new Date());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            log.error("Exception : " + e);
        }
    }

    @Async("processExecutor")
    @Scheduled(fixedRateString = "PT10S")
    public void printTimeAsyncNoLock() {
        log.info("Current time Async no lock: " + new Date());
    }
}
