package com.aserranoandres.batch_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private Job job;

    @Scheduled(cron = "0 * * * * ?") // Cada minuto
    public void runScheduledJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
            log.info("Job batch lanzado desde tarea programada");
            jobOperator.start(job, jobParameters);
        } catch (Exception e) {
            log.error("Error al ejecutar el job programado", e);
            throw new RuntimeException("Error al ejecutar el job programado", e);
        }
    }
}
