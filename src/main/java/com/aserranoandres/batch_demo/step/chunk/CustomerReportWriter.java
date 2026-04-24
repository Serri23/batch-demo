package com.aserranoandres.batch_demo.step.chunk;

import com.aserranoandres.batch_demo.model.dto.CustomerDto;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class CustomerReportWriter {

    public FlatFileItemWriter<CustomerDto> getWriter(String outputFilePath){
        return new FlatFileItemWriterBuilder<CustomerDto>()
                .name("customerReportWriter")
                .resource(new FileSystemResource(outputFilePath))
                .lineAggregator(new DelimitedLineAggregator<>(){{
                    setDelimiter(";");
                    setFieldExtractor((customer) -> new Object[]{
                            customer.getId(),
                            customer.getName(),
                            customer.getEmail(),
                            customer.getRegistrationDate()
                    });
                }})
                .headerCallback(writer -> writer.write("ID;Name;Email;RegistrationDate"))
                .build();
    }
}
