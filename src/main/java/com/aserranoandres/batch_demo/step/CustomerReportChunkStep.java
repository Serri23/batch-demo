package com.aserranoandres.batch_demo.step;

import com.aserranoandres.batch_demo.model.dto.CustomerDto;
import com.aserranoandres.batch_demo.model.entity.CustomerEntity;
import com.aserranoandres.batch_demo.step.chunk.CustomerReportProcessor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

public class CustomerReportChunkStep {

    public Step chunkStep(RepositoryItemReader<CustomerEntity> reader,
                          CustomerReportProcessor processor,
                          FlatFileItemWriter<CustomerDto> writer,
                          JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {

        return new StepBuilder("customerReportChunkStep", jobRepository)
                .<CustomerEntity, CustomerDto>chunk(5)
                .transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
