package com.aserranoandres.batch_demo.step.tasklet;

import com.aserranoandres.batch_demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendCustomerReportEmailTasklet implements Tasklet {

    private final EmailService emailService;
    private final String outputFilePath;

    @Override
    public  RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        File reportFile = new File(outputFilePath);
        if (!reportFile.exists()) {
            log.error(" El archivo de reporte no existe: {}", outputFilePath);
            throw new IllegalStateException("El archivo de reporte no fue generado: " + outputFilePath);
        }

        Long writeCount = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getStepExecutions()
                .stream()
                .filter(step -> step.getStepName().equals("customerReportChunkStep"))
                .findFirst()
                .map(StepExecution::getWriteCount)
                .orElse(0L);


        String emailBody = emailService.generateEmailBody(writeCount.intValue());

        try {
            emailService.sendEmailWithAttachment(outputFilePath, emailBody);
            log.info("Email enviado correctamente con el archivo adjunto");
        } catch (Exception e) {
            log.error(" Error al enviar el email: {}", e.getMessage(), e);
            throw e;
        }

        return RepeatStatus.FINISHED;
    }
}

