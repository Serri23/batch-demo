package com.aserranoandres.batch_demo.config;

import com.aserranoandres.batch_demo.job.CustomerReportJob;
import com.aserranoandres.batch_demo.model.dto.CustomerDto;
import com.aserranoandres.batch_demo.model.entity.CustomerEntity;
import com.aserranoandres.batch_demo.repository.CustomerRepository;
import com.aserranoandres.batch_demo.service.EmailService;
import com.aserranoandres.batch_demo.step.CustomerReportChunkStep;
import com.aserranoandres.batch_demo.step.CustomerReportTaskletStep;
import com.aserranoandres.batch_demo.step.chunk.CustomerReportProcessor;
import com.aserranoandres.batch_demo.step.chunk.CustomerReportReader;
import com.aserranoandres.batch_demo.step.chunk.CustomerReportWriter;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;

@Configuration
public class CustomerReportJobConfig {

    @Value("${customer.report.output.path}")
    private String outputPath;

    @Autowired
    private EmailService emailService;

    @Bean
    public Job job (JobRepository jobRepository,
                    Step chunkStep,
                    Step taskletStep) {
        var customerReportJob = new CustomerReportJob();
        return customerReportJob.job(jobRepository, chunkStep, taskletStep);
    }

    @Bean
    public Step chunkStep (RepositoryItemReader<CustomerEntity> reader, CustomerReportProcessor processor,
                           FlatFileItemWriter<CustomerDto> writer, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        var step = new CustomerReportChunkStep();
        return step.chunkStep(reader, processor, writer, jobRepository, transactionManager);
    }

    @Bean
    public Step taskletStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        var step = new CustomerReportTaskletStep();
        return step.taskletStep(jobRepository, transactionManager, emailService, outputFilePath());
    }

    @Bean
    public RepositoryItemReader<CustomerEntity> reader(CustomerRepository customerRepository) {
        var reader = new CustomerReportReader();
        return reader.getReader(customerRepository);
    }

    @Bean
    public CustomerReportProcessor processor() {
        return new CustomerReportProcessor();
    }

    @Bean
    public FlatFileItemWriter<CustomerDto> writer() {
        var writer = new CustomerReportWriter();
        return writer.getWriter(outputFilePath());
    }

    @Bean
    public String outputFilePath() {
        return outputPath + "CUSTOMER_REPORT_" + LocalDate.now() + ".csv";
    }

}
