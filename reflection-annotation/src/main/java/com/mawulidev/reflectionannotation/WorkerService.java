package com.mawulidev.reflectionannotation;

import org.springframework.stereotype.Service;

@Service
public class WorkerService {
    @LogExecutionTime
    public void performWork() throws InterruptedException {
        //Simulate a time consuming task
        Thread.sleep(500);
        System.out.println("Task completed");
    }
}
