package com.practice.demo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

    private final JobLauncher jobLauncher;
    private final Job importCustomerJob;

    public JobRunner(JobLauncher jobLauncher, Job importCustomerJob) {
        this.jobLauncher = jobLauncher;
        this.importCustomerJob = importCustomerJob;
    }

    @Scheduled(cron="5 * * 1 * ?")
    public void run() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        
        jobLauncher.run(importCustomerJob, jobParameters);
    }
}
