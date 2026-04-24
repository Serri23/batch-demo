package com.aserranoandres.batch_demo.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;

public class CustomerReportJob {

    public Job job(JobRepository jobRepository,
                   Step chunkStep,
                   Step taskletStep){
        return new JobBuilder("customerReportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep)
                .next(taskletStep)
                .build();
    }
}
