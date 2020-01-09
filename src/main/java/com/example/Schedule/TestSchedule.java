package com.example.Schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/17.
 */
@Component
public class TestSchedule {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void print() {
        System.out.println("haha");
    }


}
