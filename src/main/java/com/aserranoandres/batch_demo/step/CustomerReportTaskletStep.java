package com.aserranoandres.batch_demo.step;

import com.aserranoandres.batch_demo.service.EmailService;
import com.aserranoandres.batch_demo.step.tasklet.SendCustomerReportEmailTasklet;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;

public class CustomerReportTaskletStep {

    public Step taskletStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager, EmailService emailService, String outputPath) {
        return new StepBuilder("customerReportTaskletStep", jobRepository)
                .tasklet(new SendCustomerReportEmailTasklet(emailService,outputPath), transactionManager)
                .build();
    }
}
