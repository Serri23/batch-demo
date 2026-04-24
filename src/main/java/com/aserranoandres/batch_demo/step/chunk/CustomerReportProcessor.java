package com.aserranoandres.batch_demo.step.chunk;

import com.aserranoandres.batch_demo.model.dto.CustomerDto;
import com.aserranoandres.batch_demo.model.entity.CustomerEntity;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class CustomerReportProcessor implements ItemProcessor<CustomerEntity, CustomerDto> {

    @Override
    public  CustomerDto process(CustomerEntity item) {
        return new CustomerDto(
                item.getId(),
                item.getName(),
                item.getEmail(),
                item.getRegistrationDate()
        );
    }
}


