package com.aserranoandres.batch_demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${customer.report.email.from}")
    private String from;

    @Value("${customer.report.email.to}")
    private String to;

    @Value("${customer.report.email.subject}")
    private String subject;

    /**
     * Envía un email con archivo adjunto
     *
     * @param attachmentPath Ruta del archivo a adjuntar
     * @param emailBody Cuerpo del email
     * @throws MessagingException Si ocurre un error al enviar el email
     */
    public void sendEmailWithAttachment(String attachmentPath, String emailBody) throws MessagingException {
        File attachment = new File(attachmentPath);

        if (!attachment.exists()) {
            log.error("El archivo no existe: {}", attachmentPath);
            throw new IllegalArgumentException("El archivo no existe: " + attachmentPath);
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(emailBody, true);

        FileSystemResource file = new FileSystemResource(attachment);
        helper.addAttachment(attachment.getName(), file);

        log.info("Enviando email a {} con archivo adjunto: {}", to, attachment.getName());
        mailSender.send(message);
        log.info("Email enviado correctamente");
    }

    /**
     * Genera el cuerpo HTML del email
     */
    public String generateEmailBody(int totalCustomers) {
        return """
                <html>
                    <body>
                        <h2>Reporte Diario de Clientes</h2>
                        <p>Se ha generado el reporte diario de clientes.</p>
                        <p><strong>Total de clientes procesados:</strong> %d</p>
                        <p>Adjunto encontrarás el archivo CSV con el detalle completo.</p>
                        <br>
                        <p>Saludos,<br>Sistema de Reportes Batch</p>
                    </body>
                </html>
                """.formatted(totalCustomers);
    }
}

