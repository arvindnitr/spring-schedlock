package com.arvind.scheduler.springschedlock.service;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ScheduledJobService {

    /*@Scheduled(initialDelay = 1000L, fixedDelayString = "${printTime.delay}")
    public void printTime() {
        log.info("Current time: " + new Date());
    }*/

    @Scheduled(cron = "${printTime.cron}")
    @SchedulerLock(name = "TaskScheduler_scheduledPrintTimeTask",
            lockAtLeastFor = "PT3M", lockAtMostFor= "PT10M")
    public Object printTimeCron() throws InterruptedException {
        log.info("Current time from cron: " + new Date());
        Thread.sleep(4*60*1000);
        return null;
    }

    @Scheduled(cron = "${printTime.cron}")
    @SchedulerLock(name = "TaskScheduler_scheduledPrintTimeTaskFail",
            lockAtLeastFor = "PT1M", lockAtMostFor= "PT3M")
    public void printTimeCronFail() throws InterruptedException {
        log.info("Current time1 for TaskScheduler_scheduledPrintTimeTaskFail: " + new Date());
        throw new RuntimeException();
    }
}
