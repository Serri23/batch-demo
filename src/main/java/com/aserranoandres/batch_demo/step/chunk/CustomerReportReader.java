package com.aserranoandres.batch_demo.step.chunk;

import com.aserranoandres.batch_demo.model.entity.CustomerEntity;
import com.aserranoandres.batch_demo.repository.CustomerRepository;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

public class CustomerReportReader {

    public RepositoryItemReader<CustomerEntity> getReader(CustomerRepository customerRepository) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);

        return new RepositoryItemReaderBuilder<CustomerEntity>()
                .name("customerReportReader")
                .repository(customerRepository)
                .methodName("findAll")
                .sorts(sorts)
                .pageSize(10)
                .build();
    }
}
